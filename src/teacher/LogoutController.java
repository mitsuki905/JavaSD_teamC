package teacher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/accounts/logout" })
public class LogoutController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		session情報を削除する
		HttpSession session = req.getSession();
		session.invalidate();

//		logout完了ページにフォワード
        req.getRequestDispatcher("logout.jsp").forward(req, resp);


	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
