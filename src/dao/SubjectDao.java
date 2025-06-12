package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends DAO {

// 指定した科目を取得する関数
	public Subject get(String cd,School school){
		Subject subject =null;


		try {
			Connection con = getConnection();

			//データベースから学校情報を持ってきてSubjectオブジェクトを作る
			String sqlsubject = "SELECT * FROM SUBJECT WHERE CD = ?";

			PreparedStatement st1 = con.prepareStatement(sqlsubject);
			st1.setString(1, cd);
			ResultSet rs1 = st1.executeQuery();

			if (rs1.next()) {
			    subject = new Subject();
			    subject.setCd(rs1.getString("cd"));
			    subject.setName(rs1.getString("name"));
			    subject.setSchool(school);
			}
			else{
				return null;
			}
			}catch (Exception e) {
				e.printStackTrace();
			}return subject;
	}

//  科目をリスト型で返す
	public List<Subject> filter(School school) {
	    List<Subject> list = new ArrayList<>();

	    try (Connection con = getConnection()) {
	        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, school.getCd()); // 学校コードで絞る
	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            Subject sub = new Subject();
	            sub.setCd(rs.getString("cd"));
	            sub.setName(rs.getString("name"));
	            sub.setSchool(school); // 参照で渡せばOK
	            list.add(sub);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

//	科目追加　科目更新
	public boolean save(Subject subject)  {
		boolean flag = false;
		try (Connection con = getConnection()) {
	         Subject subget = get(subject.getCd(), subject.getSchool());


	         	//追加の処理
		        if(subget == null){
		        	String sql = "INSERT INTO SUBJECT (school_cd, cd, name) VALUES(?,?,?)";
		        	PreparedStatement st = con.prepareStatement(sql);
		        	st.setString(1, subject.getSchool().getCd()); // 外部キー school_cd
		        	st.setString(2, subject.getCd());             // 科目コード
		        	st.setString(3, subject.getName());           // 科目名
		        	st.executeUpdate();
		        	flag = true;
		        } else {
		        //更新の処理
		        	String sql = "UPDATE SUBJECT SET school = ? ,"
		        			+ " cd = ? ,"
		        			+ " name= ? "
		        			+ "WHERE CD = ?";
		        	PreparedStatement st = con.prepareStatement(sql);
		        	st.setString(1, subject.getSchool().getCd()); // 外部キー school_cd
		        	st.setString(2, subject.getCd());             // 科目コード
		        	st.setString(3, subject.getName());           // 科目名
		        	st.setString(4, subject.getCd());
		        	st.executeUpdate();
		        	flag = true;
		        	}
		         }catch (Exception e) {
		        	// TODO 自動生成された catch ブロック
		        	e.printStackTrace();
		        	}

		return flag;
	}

//	科目削除
	public boolean delete(Subject subject) {
		boolean flag = false;

		try (Connection con = getConnection()){
			String sql = "DELETE * FROM SUBJECT WHERE CD = ? ";
        	PreparedStatement st = con.prepareStatement(sql);
        	st.setString(1, subject.getCd());           // 科目名
        	st.executeUpdate();
        	flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}