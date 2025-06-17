package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/student/student_list" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, Exception {


		StudentDao dao = new StudentDao();
		List<Student> student = dao.getList();

		// 1. 入学年度リストの作成
        Set<Integer> yearSet = new TreeSet<>();
        for (Student s : student) {
            yearSet.add(s.getEntYear());
        }
        List<Integer> yearList = new ArrayList<>(yearSet);

        // 2. クラス番号リストの作成
        Set<Character> classSet = new TreeSet<>();
        for (Student s : student) {
            classSet.add(s.getClassNum());
        }
        List<String> classList = new ArrayList<>();

		req.setAttribute("yearList", yearList);
		req.setAttribute("classList", classList);

		req.getRequestDispatcher("student_list.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}