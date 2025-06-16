package student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;
@WebServlet(urlPatterns = { "/subject/student_update" })
public class StudentUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

//		画面から送られてきた値を取得する
		int entyear = Integer.parseInt(req.getParameter("entyear"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String num = req.getParameter("num");

		req.setAttribute("entyear", entyear);
		req.setAttribute("no", no);
		req.setAttribute("name", name);
		req.setAttribute("num", num);

		req.getRequestDispatcher("student_update.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
