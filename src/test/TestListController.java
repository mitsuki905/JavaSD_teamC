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
@WebServlet(urlPatterns = { "/test/test_list" })
public class TestListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションからログインユーザーの学校情報を取得
		System.out.println("test;;;;;;");
        HttpSession session = request.getSession();
        School school = (School) session.getAttribute("school");

        // DAOのインスタンスを生成
        SubjectDao subdao = new SubjectDao();
        StudentDao studao = new StudentDao();

        try {
            // 在校生リストを取得
            List<Student> studentList = studao.filter(school, true); // 在校生のみに絞り込み

            // --- 1. 入学年度リストの作成 ---
            // JSPの items="${yearList}" に合わせる
            Set<Integer> yearSet = new TreeSet<>();
            for (Student s : studentList) {
                yearSet.add(s.getEntYear());
            }
            List<Integer> entYearList = new ArrayList<>(yearSet);
            // ★JSPに合わせて "yearList" という名前でセット
            request.setAttribute("yearList", entYearList);

            // --- 2. クラス番号リストの作成 ---
            // JSPの items="${classList}" に合わせる
            Set<Character> classSet = new TreeSet<>();
            for (Student s : studentList) {
                classSet.add(s.getClassNum());
            }
            List<String> classNumList = new ArrayList<>();
            for (Character c : classSet) {
                classNumList.add(String.valueOf(c));
            }
            // ★JSPに合わせて "classList" という名前でセット
            request.setAttribute("classList", classNumList);

            // --- 3. 科目リストの取得 ---
            // JSPの items="${subjectList}" に合わせる
            List<Subject> subjectList = subdao.filter(school);
            // ★JSPに合わせて "subjectList" という名前でセット
            request.setAttribute("subjectList", subjectList);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング
        }

        // JSPにフォワード
        // JSPのファイル名は適宜修正してください
        request.getRequestDispatcher("test_search.jsp").forward(request, response);
    }

    // ... (postメソッドなどは省略)

	    /**
	     * フォームから送信された検索条件に基づいて成績を検索します。
	     */

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setTestListSubject(HttpServletRequest req, HttpServletResponse resp) throws Exception {}

	private void setTestListStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {}



}
