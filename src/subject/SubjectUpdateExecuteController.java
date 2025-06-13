package subject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/subject/subject_update_execute" })
public class SubjectUpdateExecuteController extends CommonServlet {

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
		boolean flag = dao.save(subject);

//		データの変更に成功したかの判定式
		if (flag != false) {
			// messageに変更完了を渡す
			req.setAttribute("message", "変更が完了しました");

			// --- 変更完了ページへフォワード ---
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, resp);
		}else {

			// 認証失敗時：入力された科目名を再表示用にセット
            req.setAttribute("name", name);
            req.setAttribute("cd", cd);

            // エラーメッセージをリクエストに追加
            req.setAttribute("errorMessage", "科目が存在していません");
            req.setAttribute("message", "変更に失敗しました");

            // ログイン画面に戻る
            req.getRequestDispatcher("subject_update.jsp").forward(req, resp);
		}



	}

}
