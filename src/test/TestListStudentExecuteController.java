package test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

//@WebServlet(urlPatterns = { "/test/test_list_student" })
public class TestListStudentExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// 検索条件をセットしたリクエストをJSPにフォワードし、画面を表示
        req.getRequestDispatcher("test_list_student.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.getRequestDispatcher("test_list_student.jsp").forward(req, resp);
	}

}
