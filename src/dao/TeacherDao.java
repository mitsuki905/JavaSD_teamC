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
		SchoolDao dao = new SchoolDao();


		try {
			Connection con = getConnection();


			/*データベースから名前が一致しているデータがあるか
			 * 調べて一致した名前があればオブジェクトを作る。
			 */
			String sql = "SELECT * FROM TEACHER WHERE ID = ?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){

				teacher = new Teacher();
				teacher.setId(rs.getString("id"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				// teacherにあるschool_cdから学校をget関数でインスタンス化
				school = dao.get(rs.getString("school_cd"));

				teacher.setSchool(school);

			}
			else{
				return null;
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return teacher;

	}

// ログイン
	public Teacher login(String id, String password){

		// teacherオブジェクトをget関数で作成
		Teacher teacher = get(id);
		// nullが返された場合loginできない
		if(teacher == null){

			return null;
		}
		else if(teacher.getPassword().equals(password)){

		// ｄｂ上にあるログインする人の情報を返す
			return teacher;
		}
		else{
			return null;
		}
	}
}