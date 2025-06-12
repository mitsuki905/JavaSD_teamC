package dao;

import java.sql.ResultSet;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends DAO {
	private String baseSql;

	private List<TestListStudent> postFilter(ResultSet rSet){}

	public List<TestListStudent> filter(Student student){}
}
