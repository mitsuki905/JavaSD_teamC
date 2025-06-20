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

		/*データベースから名前が一致しているデータがあるか
		 * 調べて一致した名前があればオブジェクトを作る。
		 */
		String sql = "SELECT * FROM TEACHER WHERE ID = ?";

		// try-with-resources構文を使用して、リソースが自動的にクローズされるように修正
		try (
			Connection con = getConnection();
			PreparedStatement st = con.prepareStatement(sql)
		) {
			st.setString(1,id);

			try (ResultSet rs = st.executeQuery()) {
				if(rs.next()){
					teacher = new Teacher();
					teacher.setId(rs.getString("id"));
					teacher.setPassword(rs.getString("password"));
					teacher.setName(rs.getString("name"));
					// teacherにあるschool_cdから学校をget関数でインスタンス化
					school = dao.get(rs.getString("school_cd"));
					teacher.setSchool(school);
				}
				// else節は不要。teacherがnullのまま返されるため
			}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			// アプリケーション全体でエラーをハンドリングできるように、実行時例外をスローする
			throw new RuntimeException("教員データの取得に失敗しました。", e);
		}
		return teacher;
	}

// ログイン
	public Teacher login(String id, String password){

		// teacherオブジェクトをget関数で作成
		Teacher teacher = get(id);

		// ログインロジックをよりシンプルに修正 (nullチェックとパスワードチェックを同時に行う)
		if(teacher != null && teacher.getPassword().equals(password)){
			// ｄｂ上にあるログインする人の情報を返す
			return teacher;
		}

		// 上記の条件に合わない場合はすべてnullを返す
		return null;
	}
}