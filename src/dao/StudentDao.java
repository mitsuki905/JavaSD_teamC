package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends DAO {

    private String baseSql = "select * from STUDENT";
    private SchoolDao schoolDao = new SchoolDao();

    public Student get(String no) {
        Student student = null;
        String sql = baseSql + " where NO = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, no);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    student = mapRowToStudent(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("学生データの取得に失敗しました。", e);
        }
        return student;
    }

    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = baseSql + " where ENT_YEAR = ? AND CLASS_NUM = ? AND IS_ATTEND = ? AND SCHOOL_CD = ? ORDER BY NO";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, entYear);
            st.setString(2, classNum);
            st.setBoolean(3, isAttend);
            st.setString(4, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()) {
                    list.add(mapRowToStudent(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("学生データのフィルタリングに失敗しました。", e);
        }
        return list;
    }

    public List<Student> filter(School school, int entYear, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = baseSql + " where ENT_YEAR = ? AND IS_ATTEND = ? AND SCHOOL_CD = ? ORDER BY CLASS_NUM, NO";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, entYear);
            st.setBoolean(2, isAttend);
            st.setString(3, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()) {
                    list.add(mapRowToStudent(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("学生データのフィルタリングに失敗しました。", e);
        }
        return list;
    }

    public List<Student> filter(School school, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        String sql = baseSql + " where IS_ATTEND = ? AND SCHOOL_CD = ? ORDER BY ENT_YEAR, CLASS_NUM, NO";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setBoolean(1, isAttend);
            st.setString(2, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()) {
                    list.add(mapRowToStudent(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("学生データのフィルタリングに失敗しました。", e);
        }
        return list;
    }

    // ResultSetの一行からStudentオブジェクトを生成するヘルパーメソッド
    private Student mapRowToStudent(ResultSet rs) throws Exception {
        Student student = new Student();
        student.setNo(rs.getString("no"));
        student.setName(rs.getString("name"));
        student.setEntYear(rs.getInt("ent_year"));
        student.setClassNum(rs.getString("class_num"));
        student.setisAttend(rs.getBoolean("is_attend"));
        // 学校情報をセット
        School school = schoolDao.get(rs.getString("school_cd"));
        student.setSchool(school);
        return student;
    }

    // saveメソッドは提供されていなかったため、参考として修正版を記載します
    // 実際にはINSERT/UPDATEのロジックが必要です
    public boolean save(Student student) {
        // このメソッドもtry-with-resourcesで実装する必要があります
        // (省略)
        return false;
    }
}