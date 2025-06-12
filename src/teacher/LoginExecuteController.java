package teacher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/accounts/loginexe" })
public class LoginExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		現在のセッションを取得「新規作成）
		HttpSession session = req.getSession();

//		画面から送られてきた値を取得する
		String id = req.getParameter("userId");
		String password = req.getParameter("password");


//		データの追加処理を行う
//		TeacherDAO(StudentDAO)に追加処理を行う
		TeacherDao dao = new TeacherDao();
		Teacher teacher = dao.login(id, password);
		try{

			if (teacher != null) {
	            // 認証成功時：ユーザー情報をセッションに保存
	            session.setAttribute("teacher", teacher);

	            // ログイン成功後、メイン画面（/main）にリダイレクト（ブラウザにURLを再要求させる）
	            resp.sendRedirect(req.getContextPath() + "/main/main");


	        } else {
	            // 認証失敗時：入力されたログイン名を再表示用にセット
	            req.setAttribute("userId", id);

	            // エラーメッセージをリクエストに追加（JSP側で表示用）
	            req.setAttribute("errorMessage", "ログインに失敗しました。idまたはパスワードが正しくありません");

	            System.out.println("error");
	            // ログイン画面に戻る（入力ミスを修正して再試行させる）
	            req.getRequestDispatcher("login.jsp").forward(req, resp);
	        }
		} catch (Exception e) {
			// エラーメッセージをリクエストに追加（JSP側で表示用）
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			e.printStackTrace();

		}
	}

}
