package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;;

public class StudentDao extends DAO {


// postfilterはデータベースから取り出した答えが入っているrsからbeanを使いデータを取り出しリストに直す関数

//	basesql
	String basesql = "select * from STUDENT";

// 生徒情報を取得
	public Student get(String school_cd){

		Student student = null;
		School school = null;

		SchoolDao dao = new SchoolDao();


		try {
			Connection con = getConnection();
			String sql = basesql + " where school_cd = ?";

			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, school_cd);
			ResultSet rs = st.executeQuery();

			if(rs.next()){

				student = new Student();
				student.setNo(rs.getString("no"));
				student.setName(rs.getString("name"));
				student.setEntYear(rs.getInt("ent_year"));
				student.setClassNum(rs.getString("class_num").charAt(0));
				student.setisAttend(rs.getBoolean("isattend"));

				school = dao.get(rs.getString("school_cd"));

				student.setSchool(school);
			}
			else {
				return null;
			}


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return student;
	}

// データベースから取ってきたデータをリストにまとめる。
	private  List<Student> postfilter(ResultSet rSet, School school){

			//絞り込んだ学生を格納するリスト
			List<Student> list = new ArrayList<>();


			Student student = null;
			SchoolDao dao = new SchoolDao();

			try {
				while(rSet.next()){

					student = new Student();
					student.setNo(rSet.getString("no"));
					student.setName(rSet.getString("name"));
					student.setEntYear(rSet.getInt("ent_year"));
					student.setClassNum(rSet.getString("class_num").charAt(0));
					student.setisAttend(rSet.getBoolean("isattend"));

					school = dao.get(rSet.getString("school_cd"));

					student.setSchool(school);
					list.add(student);
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			return list;
		}

// 学校名、入学年、クラス番号、現在在籍しているか（一番細かい絞り込み）
//	<超大事>class_numはchar型だがこの関数を使う前にString型に変更する
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {

		//絞り込んだ学生を格納するリスト
				List<Student> list = new ArrayList<>();

				try {
					Connection con = getConnection();

					String sql = basesql + " where  ENT_YEAR = ?"
							+ " AND CLASS_NUM = ?"
							+ " AND IS_ATTEND = ?"
							+ " AND SCHOOL_CD = ?";



					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, entYear);
					st.setString(2, classNum);
					st.setBoolean(3, isAttend);
					st.setString(4, school.getCd());

					ResultSet rs = st.executeQuery();

					list = postfilter(rs, school);

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return list;
			}

// 学校名、入学年、現在在籍しているか（条件Cluss_numをなくした絞り込み）
	public List<Student> filter(School school, int entYear,boolean isAttend) {

		//絞り込んだ学生を格納するリスト
				List<Student> list = new ArrayList<>();

				try {
					Connection con = getConnection();

					String sql = basesql + " where  ENT_YEAR = ?"
							+ " AND IS_ATTEND = ?"
							+ " AND SCHOOL_CD = ?";

					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, entYear);
					st.setBoolean(2, isAttend);
					st.setString(3, school.getCd());

					ResultSet rs = st.executeQuery();

					list = postfilter(rs, school);

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return list;
			}

// 学校名、現在在籍しているか（一番条件が緩い絞り込み）
	public List<Student> filter(School school,boolean isAttend) {

		//絞り込んだ学生を格納するリスト
				List<Student> list = new ArrayList<>();

				try {
					Connection con = getConnection();

					String sql = basesql + " where IS_ATTEND  = ?"
							+ " AND SCHOOL_CD = ?";

					PreparedStatement st = con.prepareStatement(sql);
					st.setBoolean(1, isAttend);
					st.setString(2, school.getCd());

					ResultSet rs = st.executeQuery();

					list = postfilter(rs, school);

				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return list;
			}
}


