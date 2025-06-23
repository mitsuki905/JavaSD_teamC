package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends DAO {

//ベースSQL
    private static final String BASE_SQL = "SELECT s.NO, s.NAME, s.ENT_YEAR, s.CLASS_NUM, s.IS_ATTEND, s.SCHOOL_CD, sc.NAME AS SCHOOL_NAME FROM STUDENT s LEFT JOIN SCHOOL sc ON s.SCHOOL_CD = sc.CD ";

    private List<Student> postFilter(ResultSet rs) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setNo(rs.getString("NO"));
            student.setName(rs.getString("NAME"));
            student.setEntYear(rs.getInt("ENT_YEAR"));
            student.setClassNum(rs.getString("CLASS_NUM"));
            student.setIsAttend(rs.getBoolean("IS_ATTEND"));

            School school = new School();
            school.setCd(rs.getString("SCHOOL_CD"));
            school.setName(rs.getString("SCHOOL_NAME"));
            student.setSchool(school);

            list.add(student);
        }
        return list;
    }

//  特定の学生情報の取得
    public Student get(String no) {
        Student student = null;
        String sql = BASE_SQL + "WHERE s.NO = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, no);

            try (ResultSet rs = st.executeQuery()) {
                List<Student> list = postFilter(rs);
                if (!list.isEmpty()) {
                    student = list.get(0);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("学生情報の取得に失敗しました。", e);
        }
        return student;
    }

//    学生一覧リストの取得
    public List<Student> getList() {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "ORDER BY s.NO";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            list = postFilter(rs);

        } catch (Exception e) {
            throw new RuntimeException("学生一覧の取得に失敗しました。", e);
        }
        return list;
    }

//    オブジェクトをリストに格納
    private List<Student> postfilter(ResultSet rSet, School school) {
        List<Student> list = new ArrayList<>();
        Student student = null;
        SchoolDao dao = new SchoolDao();

        try {
            while (rSet.next()) {
                student = new Student();
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setIsAttend(rSet.getBoolean("is_attend"));
                school = dao.get(rSet.getString("school_cd"));
                student.setSchool(school);
                list.add(student);
            }
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return list;
    }

//
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND s.IS_ATTEND = ? AND s.SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, entYear);
            st.setString(2, classNum);
            st.setBoolean(3, isAttend);
            st.setString(4, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException("学生情報の絞り込みに失敗しました。", e);
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "WHERE s.ENT_YEAR = ? AND s.IS_ATTEND = ? AND s.SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, entYear);
            st.setBoolean(2, isAttend);
            st.setString(3, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException("学生情報の絞り込みに失敗しました。", e);
        }
        return list;
    }

    public List<Student> filter(School school, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = BASE_SQL + "WHERE s.IS_ATTEND = ? AND s.SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setBoolean(1, isAttend);
            st.setString(2, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                list = postFilter(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException("学生情報の絞り込みに失敗しました。", e);
        }
        return list;
    }

    public boolean save(Student student) {
        String sql = "MERGE INTO STUDENT (NO, NAME, ENT_YEAR, CLASS_NUM, IS_ATTEND, SCHOOL_CD) "
                   + "KEY(NO) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        int count = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, student.getNo());
            st.setString(2, student.getName());
            st.setInt(3, student.getEntYear());
            st.setString(4, student.getClassNum());
            st.setBoolean(5, student.getisAttend());
            st.setString(6, student.getSchool().getCd());

            count = st.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("学生情報の保存に失敗しました。", e);
        }

        return count > 0;
    }
}