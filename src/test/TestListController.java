package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import dao.StudentDao;
import dao.SubjectDao;
import tool.CommonServlet;

/**
 * 成績管理機能の検索画面（test_search.jsp）に関する処理を行うサーブレットです。
 * 検索条件として使用するドロップダウンリストのデータを準備し、画面に表示します。
 */
@WebServlet(urlPatterns = { "/test/test_list" })
public class TestListController extends CommonServlet {

	/**
	 * HTTP GETリクエストを処理します。
	 * 検索画面の初期表示時に呼び出され、検索条件の選択肢（入学年度、クラス、科目）を
	 * データベースから取得し、JSPに渡します。
	 *
	 * @param request クライアントからのリクエストを含む HttpServletRequest オブジェクト
	 * @param response クライアントへのレスポンスを含む HttpServletResponse オブジェクト
	 * @throws ServletException サーブレットがGETリクエストを処理中にエラーが発生した場合
	 * @throws IOException リクエストの処理中に入出力エラーが発生した場合
	 */
	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // デバッグ用出力
		System.out.println("test;;;;;;");

        // セッションからログインユーザーの学校情報を取得
        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        // データベースアクセスクラス(DAO)のインスタンスを生成
        SubjectDao subdao = new SubjectDao();
        StudentDao studao = new StudentDao();

        try {
            // 指定された学校に所属する在校生の一覧を取得します
            List<Student> studentList = studao.filter(school, true); // true: 在校生のみ

            // -----------------------------------------------------------------
            // 1. 入学年度リストの作成 (JSPのドロップダウンリスト用)
            // -----------------------------------------------------------------
            // TreeSetを使い、重複を除き自動でソートされた入学年度のセットを作成
            Set<Integer> yearSet = new TreeSet<>();
            for (Student student : studentList) {
                yearSet.add(student.getEntYear());
            }
            // JSPで使いやすいようにListに変換
            List<Integer> entYearList = new ArrayList<>(yearSet);
            // "yearList"という名前でリクエストスコープに設定
            request.setAttribute("yearList", entYearList);

            // -----------------------------------------------------------------
            // 2. クラス番号リストの作成 (JSPのドロップダウンリスト用)
            // -----------------------------------------------------------------
            // TreeSetを使い、重複を除き自動でソートされたクラス番号のセットを作成
            Set<String> classSet = new TreeSet<>();
            for (Student student : studentList) {
                classSet.add(student.getClassNum());
            }
            // JSPで使いやすいようにListに変換
            List<String> classNumList = new ArrayList<>(classSet);
            // "classList"という名前でリクエストスコープに設定
            request.setAttribute("classList", classNumList);

            // -----------------------------------------------------------------
            // 3. 科目リストの取得 (JSPのドロップダウンリスト用)
            // -----------------------------------------------------------------
            // DAOを使い、学校に登録されている科目の一覧を取得
            List<Subject> subjectList = subdao.filter(school);
            // "subjectList"という名前でリクエストスコープに設定
            request.setAttribute("subjectList", subjectList);

        } catch (Exception e) {
            // データベースアクセス等でエラーが発生した場合の処理
            e.printStackTrace(); // 開発中はコンソールにスタックトレースを出力
            // 本番環境では、エラーページにフォワードするなどの処理をここに記述します
            // request.setAttribute("error", "データの取得中にエラーが発生しました。");
            // request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        // 検索条件をセットしたリクエストをJSPにフォワードし、画面を表示
        request.getRequestDispatcher("test_search.jsp").forward(request, response);
    }

	/**
	 * HTTP POSTリクエストを処理します。
	 * 検索画面のフォームから送信された検索条件に基づいて、成績データを検索します。
	 * (このメソッドは現在実装されていません)
	 *
	 * @param req クライアントからのリクエストを含む HttpServletRequest オブジェクト
	 * @param resp クライアントへのレスポンスを含む HttpServletResponse オブジェクト
	 * @throws Exception リクエストの処理中にエラーが発生した場合
	 */
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO: フォームから送信された検索条件を受け取り、成績データを検索して
		//       結果画面に渡す処理を実装します。
	}

	/**
	 * (現在未使用のプライベートメソッド)
	 */
	private void setTestListSubject(HttpServletRequest req, HttpServletResponse resp) throws Exception {}

	/**
	 * (現在未使用のプライベートメソッド)
	 */
	private void setTestListStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {}
}