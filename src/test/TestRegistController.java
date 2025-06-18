package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/test/test_regist" })
public class TestRegistController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
/*		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		コース一覧を取得する
		SubjectDao dao = new SubjectDao();
		List<Subject> subject = dao.filter(school);

//		取得した値をリクエストに保存する 変数名：subject
		req.setAttribute("subject", subject);
*/
// 		追加フォーム（test_regist.jsp）にフォワード
		req.getRequestDispatcher("test_regist.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
