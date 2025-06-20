package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends DAO {

    public Subject get(String cd, School school) {
        Subject subject = null;
        String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, cd);
            st.setString(2, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    subject = new Subject();
                    subject.setCd(rs.getString("cd"));
                    subject.setName(rs.getString("name"));
                    subject.setSchool(school);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("科目情報の取得に失敗しました。", e);
        }
        return subject;
    }

    public List<Subject> filter(School school) {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Subject sub = new Subject();
                    sub.setCd(rs.getString("cd"));
                    sub.setName(rs.getString("name"));
                    sub.setSchool(school);
                    list.add(sub);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("科目一覧の取得に失敗しました。", e);
        }
        return list;
    }

    public boolean save(Subject subject) {
        String sql = "MERGE INTO SUBJECT (SCHOOL_CD, CD, NAME) KEY(SCHOOL_CD, CD) VALUES(?, ?, ?)";
        int count = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, subject.getSchool().getCd());
            st.setString(2, subject.getCd());
            st.setString(3, subject.getName());

            count = st.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("科目情報の保存に失敗しました。", e);
        }
        return count > 0;
    }

    public boolean delete(Subject subject) {
        String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
        int count = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, subject.getCd());
            st.setString(2, subject.getSchool().getCd());

            count = st.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("科目情報の削除に失敗しました。", e);
        }
        return count > 0;
    }
}