package bean;

import java.io.Serializable;
import java.util.Map;

public class TestListSubject implements Serializable {

	private int entYear;
	private String StudentNo;
	private String StudentName;
	private String ClassNum;
	private Map<Integer,Integer> points;


	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public String getStudentNo() {
		return StudentNo;
	}
	public void setStudentNo(String studentNo) {
		StudentNo = studentNo;
	}
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	public String getClassNum() {
		return ClassNum;
	}
	public void setClassNum(String classNum) {
		ClassNum = classNum;
	}
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

}
