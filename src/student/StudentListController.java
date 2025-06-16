package student;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/student/student_list" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, Exception {
    	HttpSession session = req.getSession();

		StudentDao dao = new StudentDao();
		List<Student> yearlist = dao.getList();


		req.setAttribute("yearList", yearlist);

		req.getRequestDispatcher("student_list.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}