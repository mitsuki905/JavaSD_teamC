package student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;
@WebServlet(urlPatterns = { "/student/student_create_done" })
public class StudentCreateExecuteController extends CommonServlet {

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 現在のsessionを取得
		HttpSession session = req.getSession();

		School school = (School) session.getAttribute("school");

		System.out.println(school.getCd());

		// JSPのフォームのname属性に合わせてパラメータを取得
		int entYear = Integer.parseInt(req.getParameter("ent_year"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		boolean isAttend = true;

		if (entYear == 0){
			// 入学年度を指定していない場合
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("class_num", classNum);
			req.setAttribute("ent_year", entYear);

			// エラー時にドロップダウンリストを再設定
			this.setLists(req, school);

			// エラーメッセージをリクエストに追加 (JSP側のc:ifのキーに合わせる)
			req.setAttribute("error_ent_year", "入学年度を選択してください");
			// 登録画面に戻る
			req.getRequestDispatcher("student_create.jsp").forward(req, resp);
		}

		StudentDao dao = new StudentDao();
		Student chofuku = dao.get(no);

		// 重複がない場合新規登録をする
		if (chofuku == null) {
			// beanを使って学生の情報をまとめる
			Student student = new Student();
			student.setSchool(school);
			String i = school.getCd();
			System.out.println(i);
			student.setEntYear(entYear);
			student.setClassNum(classNum);
			student.setNo(no);
			student.setName(name);
			student.setIsAttend(isAttend);

			boolean flag = dao.save(student);

			// データの変更に成功したかの判定式
			if (flag) {
				// messageに変更完了を渡す
				req.setAttribute("message", "登録が完了しました");
				// 変更完了ページへフォワード
				System.out.println(i);
				req.getRequestDispatcher("student_create_done.jsp").forward(req, resp);
			} else {
				// 認証失敗時：入力された値を再表示用にセット
				req.setAttribute("no", no);
				req.setAttribute("name", name);
				req.setAttribute("class_num", classNum);
				req.setAttribute("ent_year", entYear);

				// エラー時にドロップダウンリストを再設定
				this.setLists(req, school);

				// エラーメッセージをリクエストに追加
				req.setAttribute("errorMessage", "予期せぬエラーが発生しました");
				// 登録画面に戻る
				req.getRequestDispatcher("student_create.jsp").forward(req, resp);
			}
		} else {
			// 学生番号が重複していた場合
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("class_num", classNum);
			req.setAttribute("ent_year", entYear);

			// エラー時にドロップダウンリストを再設定
			this.setLists(req, school);

			// エラーメッセージをリクエストに追加 (JSP側のc:ifのキーに合わせる)
			req.setAttribute("error_no", "学生番号が重複しています");
			// 登録画面に戻る
			req.getRequestDispatcher("student_create.jsp").forward(req, resp);
		}

	}

	// エラーで画面に戻す際に、ドロップダウンリストのデータを再設定するメソッド
	private void setLists(HttpServletRequest req, School school) throws Exception {
		ClassNumDao numdao = new ClassNumDao();
		List<ClassNum> classList = numdao.filter(school);
		req.setAttribute("classList", classList);

		int currentYear = LocalDate.now().getYear();
		List<Integer> yearList = new ArrayList<>();
		for (int i = currentYear - 10; i <= currentYear + 10; i++) {
			yearList.add(i);
		}
		req.setAttribute("entYear", yearList);


	}

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// このサーブレットにGETで直接アクセスされた場合は、登録画面にリダイレクトする
		resp.sendRedirect("student_create.jsp");
	}
}