package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/subject/subject_delete_done" })
public class SubjectDeleteExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		現在のsessionを取得
		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		画面から送られてきた値を取得する
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		System.out.println(cd);
		System.out.println(name);

//		beanを使って学生の情報をまとめる
		Subject subject = new Subject();
		subject.setSchool(school);
		subject.setCd(cd);
		subject.setName(name);

//		まとめたデータに変更させる
		SubjectDao dao = new SubjectDao();
		boolean flag = dao.delete(subject);

//		データの変更に成功したかの判定式
		if (flag != false) {

			// --- 削除完了ページへフォワード ---
			req.getRequestDispatcher("subject_delete_done.jsp").forward(req, resp);
		}else {


            // エラーメッセージをリクエストに追加
            req.setAttribute("errorMessage", "エラーが発生しました");

            // エラーページに飛ぶ
            req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
	}

}
