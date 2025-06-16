package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import tool.CommonServlet;

public class TestListController extends CommonServlet {

	@Override


	protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // セッションからログインユーザーの学校情報を取得
	     	HttpSession session = request.getSession();
	        School school = (School) session.getAttribute("school"); // "school"はログイン時にセットした属性名

	        TestListStudentDao studao = new TestListStudentDao();
	        SubjectDao dao = new SubjectDao();
	        TestListSubjectDao subdao = new TestListSubjectDao();

	        // ログインしていない、またはセッションが切れた場合はログインページにリダイレクト

	        // プルダウンメニュー用のデータを準備するメソッドを呼び出す

	        // JSPにフォワード
	        request.getRequestDispatcher("/jsp/test_list_subject.jsp").forward(request, response);
	    }

	    /**
	     * フォームから送信された検索条件に基づいて成績を検索します。
	     */

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}


}
