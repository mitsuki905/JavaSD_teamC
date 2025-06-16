package student;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/student/student_list" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, Exception {
    	HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

		TestListStudentDao dao = new TestListStudentDao();
		List<Student> student = dao.filter();

    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if (school !=null and class_num != null and) {

		} elseif () {

		} elseif () {}

    }
}