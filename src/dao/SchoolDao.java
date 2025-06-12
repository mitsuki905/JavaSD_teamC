package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class SchoolDao extends DAO {
	//指定した学生の学生番号を取得する
	public School get(String no){
		School school = null;


		try{
			Connection con = getConnection();
			String sql = "SELECT * FROM SCHOOL WHERE CD = ?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,no);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
			 school = new School();
			 school.setCd(rs.getString("cd"));
			 school.setName(rs.getString("name"));
			}
		}catch (Exception e){

		}
		return school;
	}
}
