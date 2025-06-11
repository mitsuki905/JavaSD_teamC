package bean;

import java.io.Serializable;

public class Student implements Serializable{

	private String no;
	private String name;
	private int entYear;
	private char classNum;
	private boolean isAttend;
	private School school;

	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public char getClassNum() {
		return classNum;
	}
	public void setClassNum(char c) {
		this.classNum = c;
	}
	public boolean isAttend() {
		return isAttend;
	}
	public void setisAttend(boolean isAttend) {
		this.isAttend = isAttend;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

}
