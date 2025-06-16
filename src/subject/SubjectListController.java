package subject;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/subject/subject_list" })
public class SubjectListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		コース一覧を取得する
		SubjectDao dao = new SubjectDao();
		List<Subject> subject = dao.filter(school);

//		取得した値をリクエストに保存する 変数名：subject
		req.setAttribute("subject", subject);

// 		追加フォーム（subject_list.jsp）にフォワード
		req.getRequestDispatcher("subject_list.jsp").forward(req, resp);


	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
