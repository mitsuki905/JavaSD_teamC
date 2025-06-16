package student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import dao.SubjectDao;
import tool.CommonServlet;

public class StudentUpdateExecuteController extends CommonServlet {

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
//		現在のsessionを取得
		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		画面から送られてきた値を取得する
		int entyear = Integer.parseInt(req.getParameter("entyear"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String num = req.getParameter("num");
		String isattend = req.getParameter("isattend");


//		beanを使って学生の情報をまとめる
		Student student = new Student();
		student.setSchool(school);
		student.setEntYear(entyear);
		student.setNo(no);
		student.setName(name);

		// 【重要】Stringからcharへの変換処理
		if (num != null && !num.isEmpty()) {
		    // 文字列が空でないことを確認してから、最初の1文字を取得
		    char classNumChar = num.charAt(0);
		    // char型でセットする
		    student.setClassNum(classNumChar);
		}

		// Stringをbooleanに変換
		// パラメータが"true"（大文字小文字を区別しない）の場合にtrue、それ以外（nullや他の文字列）の場合はfalseになる
		boolean isAttend = "true".equalsIgnoreCase(isattend);


//		まとめたデータに変更させる
		SubjectDao dao = new SubjectDao();
		boolean flag = dao.save(student);

//		データの変更に成功したかの判定式
		if (flag != false) {
			// messageに変更完了を渡す
			req.setAttribute("message", "変更が完了しました");

			// --- 変更完了ページへフォワード ---
			req.getRequestDispatcher("student_update_done.jsp").forward(req, resp);
		}else {

			// 認証失敗時：入力された科目名を再表示用にセット
			req.setAttribute("entyear", entyear);
	        req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("num", num);
            req.setAttribute("isattend", isattend);

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
