package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends DAO {

    /**
     * ResultSetをTestListSubjectのリストに変換します。
     * このメソッドは、学生一人に対して複数のテスト結果（点数）を紐付けます。
     * @param rSet データベースからの結果セット
     * @return TestListSubjectオブジェクトのリスト
     * @throws Exception ResultSetの処理中にエラーが発生した場合
     */
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        TestListSubject currentStudent = null;
        String currentStudentNo = "";

        while (rSet.next()) {
            String studentNo = rSet.getString("STUDENT_NO");

            // 学生番号が切り替わったタイミングで、新しいTestListSubjectオブジェクトを生成
            if (!studentNo.equals(currentStudentNo)) {
                currentStudent = new TestListSubject();
                currentStudent.setEntYear(rSet.getInt("ENT_YEAR"));
                currentStudent.setClassNum(rSet.getString("CLASS_NUM"));
                currentStudent.setStudentNo(rSet.getString("STUDENT_NO"));
                currentStudent.setStudentName(rSet.getString("STUDENT_NAME"));
                list.add(currentStudent);
                currentStudentNo = studentNo;
            }

            // テスト結果が存在する場合（LEFT JOINでTEST側のカラムがNULLでない場合）のみ点数をセット
            if (rSet.getObject("TEST_NO") != null) {
                int testNo = rSet.getInt("TEST_NO");
                int point = rSet.getInt("POINT");
                // テストの回数と点数をMapに追加
                currentStudent.putPoint(testNo, point);
            }
        }
        return list;
    }

    /**
     * 指定された条件で成績情報をフィルタリング検索します。
     *
     * 【重要：修正点】
     * 元のコードではWHERE句で科目CDを絞っていたため、指定科目の成績がない学生が表示されませんでした。
     * 仕様通り、指定クラスの全学生を表示し、成績の有無を別途表示するために、
     * 科目CDの条件をLEFT JOINのON句に移動しました。
     *
     * @param entYear 入学年度
     * @param classNum クラス番号
     * @param subject 絞り込み対象の科目オブジェクト
     * @param school 学生が所属する学校オブジェクト
     * @return 検索結果のTestListSubjectのリスト
     * @throws Exception データベースアクセス中にエラーが発生した場合
     */
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        // パラメータを格納するリスト
        List<Object> params = new ArrayList<>();

        // SQL文の骨格を定義。STUDENTテーブルを主軸にする
        StringBuilder sql = new StringBuilder(
            "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, " +
            "t.NO AS TEST_NO, t.POINT " +
            "FROM STUDENT s " +
            "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND s.SCHOOL_CD = t.SCHOOL_CD "
        );

        // 【修正点】科目条件をLEFT JOINのON句に追加。これにより、成績がない学生も結果に含まれるようになる
        if (subject != null && subject.getCd() != null && !subject.getCd().isEmpty()) {
            sql.append("AND t.SUBJECT_CD = ? ");
            params.add(subject.getCd());
        }

        // WHERE句の条件をリストで管理
        List<String> whereClauses = new ArrayList<>();

        // 学校コードは必須条件
        whereClauses.add("s.SCHOOL_CD = ?");
        params.add(school.getCd());

        // 入学年度が指定されている場合
        if (entYear != 0) {
            whereClauses.add("s.ENT_YEAR = ?");
            params.add(entYear);
        }
        // クラス番号が指定されている場合
        if (classNum != null && !classNum.isEmpty() && !classNum.equals("0")) {
            whereClauses.add("s.CLASS_NUM = ?");
            params.add(classNum);
        }

        // WHERE句をSQL文に結合
        if (!whereClauses.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", whereClauses));
        }

        // 表示順を学生番号でソート
        sql.append(" ORDER BY s.NO");

        // データベースに接続してSQLを実行
        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql.toString())
        ) {
            // パラメータをPreparedStatementにセット
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            // SQLを実行し、結果セットを取得
            try (ResultSet rs = st.executeQuery()) {
                // 結果セットをオブジェクトのリストに変換
                list = postFilter(rs);
            }
        } catch (Exception e) {
            // エラーログを出力し、呼び出し元に例外をスロー
            e.printStackTrace();
            throw e;
        }

        return list;
    }
}