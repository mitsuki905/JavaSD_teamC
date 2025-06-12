package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject; // Subjectクラスをインポート
import bean.TestListStudent;

public class TestListStudentDao extends DAO {

    // 共通のSQL骨格 (変更なし)
    private String baseSql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, "
                         + "t.NO AS TEST_NO, t.POINT, t.SUBJECT_CD " // 科目CDもSELECTに含めるとデバッグ等で便利
                         + "FROM STUDENT s "
                         + "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND s.SCHOOL_CD = t.SCHOOL_CD";

    /**
     * ResultSetをリストに変換するヘルパーメソッド (変更なし)
     */
    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        TestListStudent currentStudent = null;
        String currentStudentNo = "";

        while (rSet.next()) {
            String studentNo = rSet.getString("STUDENT_NO");

            if (!studentNo.equals(currentStudentNo)) {
                currentStudent = new TestListStudent();
                currentStudent.setEntYear(Integer.parseInt("ENT_YEAR"));
                currentStudent.setClassNum(rSet.getString("CLASS_NUM"));
                currentStudent.setStudentNo(rSet.getString("STUDENT_NO"));
                currentStudent.setStudentName(rSet.getString("STUDENT_NAME"));
                list.add(currentStudent);
                currentStudentNo = studentNo;
            }

            if (rSet.getObject("TEST_NO") != null) {
                int testNo = rSet.getInt("TEST_NO");
                int point = rSet.getInt("POINT");
                currentStudent.setPoint(testNo);
            }
        }
        return list;
    }

    /**
     * 学生のテストリストを検索する。
     * 科目(Subject)での絞り込みにも対応。
     *
     * @param entYear  入学年度（0の場合は全年度対象）
     * @param classNum クラス番号（nullや空の場合は全クラス対象）
     * @param subject  科目（nullまたは科目コードが未設定の場合は全科目対象）
     * @param school   学校情報
     * @return 検索結果のリスト
     * @throws Exception
     */
    public List<TestListStudent> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        // 1. SQLの動的構築
        StringBuilder sql = new StringBuilder(baseSql);

        List<String> whereClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // 学校コードは必須
        whereClauses.add("s.SCHOOL_CD = ?");
        params.add(school.getCd());

        // 入学年度で絞り込み
        if (entYear != 0) {
            whereClauses.add("s.ENT_YEAR = ?");
            params.add(entYear);
        }

        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            whereClauses.add("s.CLASS_NUM = ?");
            params.add(classNum);
        }

        // ★★★ ここが追加されたロジック ★★★
        // 科目で絞り込み
        if (subject != null && subject.getCd() != null && !subject.getCd().isEmpty()) {
            // TESTテーブルのSUBJECT_CDカラムで絞り込む
            whereClauses.add("t.SUBJECT_CD = ?");
            params.add(subject.getCd());
        }

        // 組み立てたWHERE句をSQLに追加
        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        // ソート順を追加
        sql.append(" ORDER BY s.ENT_YEAR, s.CLASS_NUM, s.NO");

        // 2. DB接続と実行
        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql.toString())
        ) {
            // パラメータをセット
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            // SQLを実行し、結果をpostFilterで整形
            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
}