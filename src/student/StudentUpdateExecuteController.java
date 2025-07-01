package student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;
@WebServlet(urlPatterns = { "/student/student_update_done" })
public class StudentUpdateExecuteController extends CommonServlet {

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
//		現在のsessionを取得
		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		画面から送られてきた値を取得する
		int entYear = Integer.parseInt(req.getParameter("entYear"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String isAttendParam = req.getParameter("isAttend");
		String classNum = req.getParameter("classNum");


//		beanを使って学生の情報をまとめる
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setSchool(school);

		System.out.println(entYear);
		System.out.println(classNum);

		// Stringをbooleanに変換
		// パラメータが"true"（大文字小文字を区別しない）の場合にtrue、それ以外（nullや他の文字列）の場合はfalseになる
		boolean isAttend = "TRUE".equalsIgnoreCase(isAttendParam);


//		まとめたデータに変更させる
		StudentDao dao = new StudentDao();


		boolean flag = dao.save(student);


//		データの変更に成功したかの判定式
		if (flag != false) {
			// messageに変更完了を渡す
			req.setAttribute("message", "変更が完了しました");

			// --- 変更完了ページへフォワード ---
			req.getRequestDispatcher("student_update_done.jsp").forward(req, resp);
		}else {

			// 認証失敗時：入力された学生情報を再表示用にセット
			req.setAttribute("entYear", entYear);
	        req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("fClassNum",classNum );
            req.setAttribute("isAttend", isAttend);

            // エラーメッセージをリクエストに追加
            req.setAttribute("errorMessage", "学生が存在していません");
            req.setAttribute("message", "変更に失敗しました");

            // ログイン画面に戻る
            req.getRequestDispatcher("student_update.jsp").forward(req, resp);
		}


	}
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}


}
