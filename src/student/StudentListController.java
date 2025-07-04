package student;

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
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/student/student_list" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, Exception {

		// DAOのインスタンスを生成
        StudentDao studao = new StudentDao();
        HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

        try {
            // 学生リストを取得
            List<Student> studentList = studao.getList(school);

            // --- 入学年度リストの作成 ---
            // JSPの items="${yearList}" に合わせる
            Set<Integer> yearSet = new TreeSet<>();
            for (Student s : studentList) {
                yearSet.add(s.getEntYear());
            }
            List<Integer> entYearList = new ArrayList<>(yearSet);
            // JSPに合わせて "yearList" という名前でセット
            req.setAttribute("yearList", entYearList);

            // --- クラス番号リストの作成 ---
            // JSPの items="${classList}" に合わせる
            Set<String> classSet = new TreeSet<>();
            for (Student s : studentList) {
                classSet.add(s.getClassNum());
            }
            List<String> classList = new ArrayList<>();
            for (String c : classSet) {
                classList.add(String.valueOf(c));
            }
            // JSPに合わせて "classList" という名前でセット
            req.setAttribute("classList", classList);




        } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング
        }

		req.getRequestDispatcher("student_list.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {


//		現在のsessionを取得
        HttpSession session = req.getSession();
        School school = (School) session.getAttribute("school");


//		画面から送られてきた値を取得する
		int year = Integer.parseInt(req.getParameter("year"));
		String classItem = req.getParameter("classItem");

		// StudentListController.java (doPost メソッド内)
		String isattendParam = req.getParameter("isAttend"); // JSPから送られてくる名前

		boolean isAttend = "TRUE".equalsIgnoreCase(isattendParam);

		List<Student> students = null;

		// DAOのインスタンスを生成
		StudentDao studao = new StudentDao();

		// 再取得用のリストを設定

		try {
			// 学生リストを取得
			List<Student> studentList = studao.getList(school);

			// --- 入学年度リストの作成 ---
			// JSPの items="${yearList}" に合わせる
			Set<Integer> yearSet = new TreeSet<>();
			for (Student s : studentList) {
				yearSet.add(s.getEntYear());
			}
			List<Integer> entYearList = new ArrayList<>(yearSet);
			// JSPに合わせて "yearList" という名前でセット
			req.setAttribute("yearList", entYearList);

			// --- クラス番号リストの作成 ---
			// JSPの items="${classList}" に合わせる
			Set<String> classSet = new TreeSet<>();
			for (Student s : studentList) {
				classSet.add(s.getClassNum());
			}
			List<String> classList = new ArrayList<>();
			for (String c : classSet) {
				classList.add(String.valueOf(c));
			}
			// JSPに合わせて "classList" という名前でセット
			req.setAttribute("classList", classList);

		} catch (Exception e) {
			e.printStackTrace();
			// エラーハンドリング
		}

		if (year == 0 && "0".equals(classItem) && !isAttend) {
			// studentsに全学生リスト
			students = studao.getList(school);
		} else if (!"0".equals(classItem)){
			if (year == 0) {
				req.setAttribute("errorMessage", "クラスを指定する場合は入学年度も指定してください");
			}
			students = studao.filter(school, year, classItem, isAttend);

		} else if (year != 0) {
			students = studao.filter(school, year, isAttend);
		} else {
			students = studao.filter(school, isAttend);
		}

		req.setAttribute("fEntYear", year);      // JSPの ${fEntYear} に対応
		req.setAttribute("fClassNum", classItem);    // JSPの ${fClassNum} に対応
		req.setAttribute("isAttend", isAttend);     // JSPの ${isAttend} に対応
		req.setAttribute("students", students);

        req.getRequestDispatcher("student_list.jsp").forward(req, resp);













    }
}