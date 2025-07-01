package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends DAO {

    /**
     * このDAOで共通して使用するSQLの骨格部分。
     * SELECT句とJOIN句を定義しておくことで、各メソッドでのコードの重複を減らし、
     * テーブル構造の変更があった場合にこの部分のみを修正すれば済むようにしています。
     * カラム名には別名(AS)を付けて、ResultSetから取り出す際のキーを明確にしています。
     */
    private static final String baseSql = "SELECT "
                                        + "  t.point, t.no as test_no, "
                                        + "  s.no as student_no, s.name as student_name, s.ent_year, s.class_num, "
                                        + "  sub.cd as subject_cd, sub.name as subject_name "
                                        + "FROM test t "
                                        + "JOIN student s ON t.student_no = s.no AND t.school_cd = s.school_cd "
                                        + "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd ";

    /**
     * 主キーに基づいて一件のTestデータを取得します。
     * @param student 学生情報を持つStudentオブジェクト
     * @param subject 科目情報を持つSubjectオブジェクト
     * @param school 学校情報を持つSchoolオブジェクト
     * @param no テストの回数
     * @param point テストの点数
     * @return 検索結果のTestオブジェクト。見つからない場合はnullを返す。
     */
    public Test get(Student student, Subject subject, School school, int no, int point) {
        Test test = null;
        String sql = baseSql + "WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ? AND t.point = ?";

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, student.getNo());
            pstmt.setString(2, subject.getCd());
            pstmt.setString(3, school.getCd());
            pstmt.setInt(4, no);
            pstmt.setInt(5, point);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<Test> list = postFilter(rs, school);
                if (!list.isEmpty()) {
                    test = list.get(0);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Testデータの取得に失敗しました。", e);
        }
        return test;
    }

    /**
     * 複数の条件で絞り込んだTestデータのリストを取得します。
     * 各引数が指定されていない場合(0, null, 空文字など)は、その検索条件を無視します。
     * @param entYear 入学年度。0の場合は無視されます。
     * @param classNum クラス番号。nullまたは空の場合は無視されます。
     * @param subject 科目オブジェクト。nullの場合は無視されます。
     * @param num テスト回数。0の場合は無視されます。
     * @param school 学校オブジェクト。必須の検索条件です。
     * @return 検索結果のTestオブジェクトのリスト。
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) {
        List<Test> list = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(baseSql);
        List<String> conditions = new ArrayList<>();

        conditions.add("t.school_cd = ?");

        if (entYear > 0) { conditions.add("s.ent_year = ?"); }
        if (classNum != null && !classNum.isEmpty() && !classNum.equals("0")) { conditions.add("s.class_num = ?"); }
        if (subject != null && subject.getCd() != null) { conditions.add("t.subject_cd = ?"); }
        if (num > 0) { conditions.add("t.no = ?"); }

        if (!conditions.isEmpty()) {
            sqlBuilder.append("WHERE ");
            sqlBuilder.append(String.join(" AND ", conditions));
        }
        sqlBuilder.append(" ORDER BY s.ent_year, s.class_num, s.no, t.subject_cd");

        String sql = sqlBuilder.toString();

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            int paramIndex = 1;
            pstmt.setString(paramIndex++, school.getCd());

            if (entYear > 0) { pstmt.setInt(paramIndex++, entYear); }
            if (classNum != null && !classNum.isEmpty() && !classNum.equals("0")) { pstmt.setString(paramIndex++, classNum); }
            if (subject != null && subject.getCd() != null) { pstmt.setString(paramIndex++, subject.getCd()); }
            if (num > 0) { pstmt.setInt(paramIndex++, num); }

            try (ResultSet rs = pstmt.executeQuery()) {
                list = postFilter(rs, school);
            }
        } catch (Exception e) {
            throw new RuntimeException("Testデータのフィルタリングに失敗しました。", e);
        }
        return list;
    }

    /**
     * 複数のTestオブジェクトをまとめてデータベースに保存（登録または更新）します。
     * トランザクション処理を行い、全てのデータが正常に保存できた場合のみ処理を確定(commit)します。
     * @param list 保存するTestオブジェクトのリスト
     * @return 全ての保存が成功した場合はtrue、一件でも失敗した場合はfalse
     */
    public boolean save(List<Test> list) {
        if (list == null || list.isEmpty()) return true;

        String sql = "MERGE INTO test (student_no, subject_cd, school_cd, no, class_num, point) "
                   + "KEY(student_no, subject_cd, school_cd, no) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                for (Test test : list) {
                    pstmt.setString(1, test.getStudent().getNo());
                    pstmt.setString(2, test.getSubject().getCd());
                    pstmt.setString(3, test.getSchool().getCd());
                    pstmt.setInt(4, test.getNo());
                    pstmt.setString(5, test.getClassNum());
                    pstmt.setInt(6, test.getPoint());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            con.commit();
            return true;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rbex) {
                    e.addSuppressed(rbex);
                }
            }
            throw new RuntimeException("Testデータの保存に失敗しました。", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 複数のTestオブジェクトをまとめてデータベースから削除します。
     * トランザクション処理を行い、全てのデータが正常に削除できた場合のみ処理を確定(commit)します。
     * @param list 削除するTestオブジェクトのリスト
     * @return 全ての削除が成功した場合はtrue、一件でも失敗した場合はfalse
     */
    public boolean delete(List<Test> list) {
        if (list == null || list.isEmpty()) return true;
        String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                for (Test test : list) {
                    pstmt.setString(1, test.getStudent().getNo());
                    pstmt.setString(2, test.getSubject().getCd());
                    pstmt.setString(3, test.getSchool().getCd());
                    pstmt.setInt(4, test.getNo());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            con.commit();
            return true;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rbex) {
                    e.addSuppressed(rbex);
                }
            }
            throw new RuntimeException("Testデータの削除に失敗しました。", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Test> postFilter(ResultSet rs, School school) throws SQLException {
        List<Test> list = new ArrayList<>();
        while (rs.next()) {
            Test test = new Test();
            Student student = new Student();
            Subject subject = new Subject();

            student.setNo(rs.getString(3));
            student.setName(rs.getString(4));
            student.setEntYear(rs.getInt(5));
            student.setSchool(school);

            String classNumStr = rs.getString(6);
            if (classNumStr != null && !classNumStr.isEmpty()) {
                student.setClassNum(classNumStr);
                test.setClassNum(classNumStr);
            }

            subject.setCd(rs.getString(7));
            subject.setName(rs.getString(8));
            subject.setSchool(school);

            test.setPoint(rs.getInt(1));
            test.setNo(rs.getInt(2));
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);

            list.add(test);
        }
        return list;
    }

    private boolean save(Test test, Connection connection) throws SQLException {
        String sql = "MERGE INTO test (student_no, subject_cd, school_cd, no, class_num, point) "
                   + "KEY(student_no, subject_cd, school_cd, no) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, test.getStudent().getNo());
            pstmt.setString(2, test.getSubject().getCd());
            pstmt.setString(3, test.getSchool().getCd());
            pstmt.setInt(4, test.getNo());
            pstmt.setString(5, test.getClassNum());
            pstmt.setInt(6, test.getPoint());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private boolean delete(Test test, Connection connection) throws SQLException {
        String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, test.getStudent().getNo());
            pstmt.setString(2, test.getSubject().getCd());
            pstmt.setString(3, test.getSchool().getCd());
            pstmt.setInt(4, test.getNo());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

//  削除機能が追加によりクラス図にない
    public Test get(School school, String studentNo, String subjectCd, int num) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM test WHERE school_cd = ? AND student_no = ? AND subject_cd = ? AND num = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, school.getCd());
            stmt.setString(2, studentNo);
            stmt.setString(3, subjectCd);
            stmt.setInt(4, num);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Test test = new Test();
                test.setSchool(school);
                Student student = new Student();
                student.setNo(studentNo);
                test.setStudent(student);
                Subject subject = new Subject();
                subject.setCd(subjectCd);
                test.setSubject(subject);
                test.setNo(num);
                test.setPoint(rs.getInt("point"));
                test.setClassNum(rs.getString("class_num"));
                return test;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}