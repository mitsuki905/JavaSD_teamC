package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDao extends DAO {

//  一致する名前があるか判定
	public Teacher get(String id){

		Teacher teacher = null;
		School school = null;

		try {
			Connection con = getConnection();

			//データベースから学校情報を持ってきてSchoolオブジェクトを作る
			String sqlschool = "SELECT * FROM SCHOOL";

			PreparedStatement st1 = con.prepareStatement(sqlschool);
			ResultSet rs1 = st1.executeQuery();

			if (rs1.next()) {
			    school = new School();
			    school.setCd(rs1.getString("cd"));
			    school.setName(rs1.getString("name"));
			}


			/*データベースから名前が一致しているデータがあるか
			 * 調べて一致した名前があればオブジェクトを作る。
			 */
			String sqlName = "SELECT * FROM TEACHER WHERE ID =?";

			PreparedStatement st = con.prepareStatement(sqlName);
			st.setString(1,id);
			ResultSet rs = st.executeQuery();


			if(rs.next()){



			}
			else{
				//一致する名前がない場合の処理
			}


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return teacher;

	}

// ログイン
	public Teacher login(String id, String password){

		Teacher teacher = null;
		School school = null;

		try {
			Connection con = getConnection();

			//データベースから学校情報を持ってきてSchoolオブジェクトを作る
			//teacherオブジェクトを完成させるため
			String sqlschool = "SELECT * FROM SCHOOL";

			PreparedStatement st1 = con.prepareStatement(sqlschool);
			ResultSet rs1 = st1.executeQuery();

			if (rs1.next()) {
			    school = new School();
			    school.setCd(rs1.getString("cd"));
			    school.setName(rs1.getString("name"));
			}

			// IDとPASSWORDが両方一致するデータを取ってくるSQL
			String loginSql = "select ID,PASSWORD from TEACHER where ID = ? and PASSWORD = ?";

			PreparedStatement st = con.prepareStatement(loginSql);
			st.setString(1,id);
			st.setString(2,password);
			ResultSet rs = st.executeQuery();

			if(rs.next()){

				teacher = new Teacher();
				teacher.setId(rs.getString("id"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSchool(school);

			}

			return null;


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
