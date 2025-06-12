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

    private String baseSql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, "
                         + "t.NO AS TEST_NO, t.POINT, t.SUBJECT_CD "
                         + "FROM STUDENT s "
                         + "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND s.SCHOOL_CD = t.SCHOOL_CD";

    /**
     * ResultSetをTestListSubjectのリストに変換する。
     */
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        TestListSubject currentStudent = null;
        String currentStudentNo = "";

        while (rSet.next()) {
            String studentNo = rSet.getString("STUDENT_NO");

            if (!studentNo.equals(currentStudentNo)) {
                currentStudent = new TestListSubject();
                // 正しい型とカラム名で値を取得
                currentStudent.setEntYear(rSet.getInt("ENT_YEAR"));
                currentStudent.setClassNum(rSet.getString("CLASS_NUM"));
                currentStudent.setStudentNo(rSet.getString("STUDENT_NO"));
                currentStudent.setStudentName(rSet.getString("STUDENT_NAME"));
                list.add(currentStudent);
                currentStudentNo = studentNo;
            }

            if (rSet.getObject("TEST_NO") != null) {
                int testNo = rSet.getInt("TEST_NO");
                int point = rSet.getInt("POINT");
                // テストの結果を追加
                currentStudent.putPoint(testNo, point);
            }
        }
        return list;
    }

    /**
     * 学生のテストリストを検索する。
     */
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        //SQLを結合する
        StringBuilder sql = new StringBuilder(baseSql);
        List<String> whereClauses = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        whereClauses.add("s.SCHOOL_CD = ?");
        params.add(school.getCd());
        //条件に応じてWHERE句を組み立てる
        if (entYear != 0) {
            whereClauses.add("s.ENT_YEAR = ?");
            params.add(entYear);
        }
        if (classNum != null && !classNum.isEmpty()) {
            whereClauses.add("s.CLASS_NUM = ?");
            params.add(classNum);
        }
        if (subject != null && subject.getCd() != null && !subject.getCd().isEmpty()) {
            whereClauses.add("t.SUBJECT_CD = ?");
            params.add(subject.getCd());
        }
        //最終的にここで組み立てを行う
        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        sql.append(" ORDER BY s.ENT_YEAR, s.CLASS_NUM, s.NO");
        //DBに接続
        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql.toString())
        ) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = st.executeQuery()) {
                // ここで型が一致するので、コンパイルエラーが解消される
                list = postFilter(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
}