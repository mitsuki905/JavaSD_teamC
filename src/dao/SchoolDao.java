package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Student;

public class SchoolDao extends DAO {
	//指定した学生の学生番号を取得する
	public School get(String no){
		School school = null;
		Student student = null;

		try{
			Connection con = getConnection();
			String sql = "SELECT * FROM STUDENT";

			PreparedStatement st1 = con.prepareStatement(sql);
			ResultSet rs1 = st1.executeQuery();

			if (rs1.next()) {
			    student = new Student();
			    student.setNo(rs1.getString("no"));
			    student.setName(rs1.getString("name"));
			    student.setEntYear(Integer.parseInt(rs1.getString("entYear")));
			    student.setClassNum(rs1.getString("classNum").charAt(0));
			    student.setisAttend(rs1.getBoolean("isAttend"));
			    student.setSchool(school);
			}
		}catch (Exception e){

		}
		return school;
	}
}
