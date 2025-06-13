package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/subject/subject_update" })
public class SubjectUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

//		画面から送られてきた値を取得する
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		req.setAttribute("cd", cd);
		req.setAttribute("name", name);
		


	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
