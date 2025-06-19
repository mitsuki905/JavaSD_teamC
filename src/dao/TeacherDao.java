package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDao extends DAO {

    public Teacher get(String id) {
        Teacher teacher = null;
        SchoolDao schoolDao = new SchoolDao();
        String sql = "SELECT * FROM TEACHER WHERE ID = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    teacher = new Teacher();
                    teacher.setId(rs.getString("id"));
                    teacher.setPassword(rs.getString("password"));
                    teacher.setName(rs.getString("name"));
                    School school = schoolDao.get(rs.getString("school_cd"));
                    teacher.setSchool(school);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("教員データの取得に失敗しました。", e);
        }
        return teacher;
    }

    public Teacher login(String id, String password) {
        Teacher teacher = get(id);
        if (teacher != null && teacher.getPassword().equals(password)) {
            return teacher;
        }
        return null;
    }
}