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
		HttpSession session = req.getSession();
		session.invalidate();
		resp.sendRedirect(req.getContextPath() + "/accounts/login");

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
