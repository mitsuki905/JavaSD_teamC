package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class ClassNumDao extends DAO {
	public List<String> filter(School school) {
		try (Connection con = getConnection()){
			String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, school.getCd()); // 学校コードで絞る
	        ResultSet rs = st.executeQuery();
		} catch (Exception e){

		}
	}
}
