package teacher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

/**
 * ログインページ（ログイン画面）を表示するコントローラ。
 * URLパターン /login にマッピングされている。
 */

@WebServlet(urlPatterns = { "/accounts/login" })
public class LoginController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.getRequestDispatcher("login.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {


	}

}
