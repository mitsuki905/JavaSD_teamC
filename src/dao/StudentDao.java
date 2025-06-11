package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Student;

public class StudentDao extends DAO {

	
	public Student get(String no){
		
		Student student = null;
		School school = null;
		
		
		try {
			Connection con = getConnection();
			String sql = "select * from STUDENT where NO = ?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, no);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()){
				
				student = new Student();
				student.setNo(rs.getString("no"));
				student.setName(rs.getString("name"));
				student.setEntYear(rs.getInt("ent_year"));
				student.setClassNum(rs.getString("class_num").charAt(0));
				student.setisAttend(rs.getBoolean("isattend"));
				student.setSchool(school);
			}
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		
	}
	
}
