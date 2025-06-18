package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/test/test_regist" })
public class TestRegistController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");


// 		追加フォーム（test_regist.jsp）にフォワード
		req.getRequestDispatcher("test_regist.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
