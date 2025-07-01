package subject;



import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/subject/subject_delete" })
public class SubjectDeleteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		画面から送られてきた値を取得する
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

//		取得した値を保存する
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		// 変更ページへフォワード ---
		req.getRequestDispatcher("subject_delete.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
