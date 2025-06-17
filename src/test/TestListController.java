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
	    HttpSession session = request.getSession();
	    School school = (School) session.getAttribute("school");

	    // DAOのインスタンスを生成
	    SubjectDao subdao = new SubjectDao();
	    StudentDao studao = new StudentDao();

	    try {
	        // 在校生リストを取得 (schoolとisAttend=trueで絞り込むのが望ましい)
	        // ※getList()が全校の全生徒を返すなら、filter(school, true)の方が良いです
	        List<Student> studentList = studao.filter(school, true); // 在校生のみに絞り込み

	        // 1. 入学年度リストの作成
	        Set<Integer> yearSet = new TreeSet<>();
	        for (Student s : studentList) {
	            yearSet.add(s.getEntYear());
	        }
	        List<Integer> entYearList = new ArrayList<>(yearSet);

	        // 2. クラス番号リストの作成
	        Set<Character> classSet = new TreeSet<>();
	        for (Student s : studentList) {
	            classSet.add(s.getClassNum());
	        }
	        List<String> classNumList = new ArrayList<>();
	        for (Character c : classSet) {
	            classNumList.add(String.valueOf(c));
	        }

	        // 3. 科目リストの取得
	        List<Subject> subjectList = subdao.filter(school);

	        // ★★★ JSPの変数名に合わせるための修正 ★★★
	        request.setAttribute("yearList", entYearList);     // "ent_year_list" -> "yearList"
	        request.setAttribute("classList", classNumList);   // "class_num_list" -> "classList"
	        request.setAttribute("subjectList", subjectList);  // 科目用に新設
	        // 回数(testNo)用のリストは、必要ならここで準備します

	    } catch (Exception e) {
	        e.printStackTrace();
	        // エラーハンドリング
	    }

	        // JSPにフォワード
	    // パスは環境に合わせてください (例: "/jsp/test_list.jsp")
	    request.getRequestDispatcher("test_search.jsp").forward(request, response);
	    }

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
