package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class TeacherDao extends DAO {

// 先生のログイン機能 光男作ります

	public Teacher get(String id){

		Teacher teacher = null;

		try {
			Connection con = getConnection();

			String sql = "SELECT NAME FROM TEACHER WHERE NAME =?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){

				teacher = new Teacher();
				teacher.setId(rs.getString("id"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSchool("school");

			}






		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}



}
