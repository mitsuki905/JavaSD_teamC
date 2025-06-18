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

        try {
            // 学生リストを取得
            List<Student> studentList = studao.getList();

            // --- 1. 入学年度リストの作成 ---
            // JSPの items="${yearList}" に合わせる
            Set<Integer> yearSet = new TreeSet<>();
            for (Student s : studentList) {
                yearSet.add(s.getEntYear());
            }
            List<Integer> entYearList = new ArrayList<>(yearSet);
            // ★JSPに合わせて "yearList" という名前でセット
            req.setAttribute("yearList", entYearList);

            // --- 2. クラス番号リストの作成 ---
            // JSPの items="${classList}" に合わせる
            Set<String> classSet = new TreeSet<>();
            for (Student s : studentList) {
                classSet.add(s.getClassNum());
            }
            List<String> classList = new ArrayList<>();
            for (String c : classSet) {
                classList.add(String.valueOf(c));
            }
            // ★JSPに合わせて "classList" という名前でセット
            req.setAttribute("classList", classList);



        } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング
        }

		req.getRequestDispatcher("student_list.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// DAOのインスタンスを生成
        StudentDao studao = new StudentDao();

        try {
            // 学生リストを取得
            List<Student> studentList = studao.getList();

            // --- 1. 入学年度リストの作成 ---
            // JSPの items="${yearList}" に合わせる
            Set<Integer> yearSet = new TreeSet<>();
            for (Student s : studentList) {
                yearSet.add(s.getEntYear());
            }
            List<Integer> entYearList = new ArrayList<>(yearSet);
            // ★JSPに合わせて "yearList" という名前でセット
            req.setAttribute("yearList", entYearList);

            // --- 2. クラス番号リストの作成 ---
            // JSPの items="${classList}" に合わせる
            Set<String> classSet = new TreeSet<>();
            for (Student s : studentList) {
                classSet.add(s.getClassNum());
            }
            List<String> classList = new ArrayList<>();
            for (String c : classSet) {
                classList.add(String.valueOf(c));
            }
            // ★JSPに合わせて "classList" という名前でセット
            req.setAttribute("classList", classList);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング
        }

//		現在のsessionを取得
        HttpSession session = req.getSession();
        School school = (School) session.getAttribute("school");


//		画面から送られてきた値を取得する
		int year = Integer.parseInt(req.getParameter("year"));
		String classItem = req.getParameter("classItem");
		String isattend = req.getParameter("isattend");

		boolean isAttend = "TRUE".equalsIgnoreCase(isattend);

		List<Student> students = null;
//

		if (classItem == "0") {

			if (year == 0) {
				if (isattend != "TRUE") {
					// 何も条件が指定されていない時jspに同じ情報を送り返す
					req.getRequestDispatcher("student_list.jsp").forward(req, resp);

				} else {
					// 在籍中のみの絞り込み
					students = studao.filter(school, isAttend);
					}

				// 在籍中＋入学年度の絞り込み
			} else {
				students = studao.filter(school, year, isAttend);
			}

				// 在籍中＋入学年度＋クラス番号の絞り込み
		} else {
			students = studao.filter(school, year, classItem, isAttend);
		}

		req.setAttribute("fEntYear", year);      // JSPの ${fEntYear} に対応
        req.setAttribute("fClassNum", classItem);    // JSPの ${fClassNum} に対応
        req.setAttribute("isattend", isAttend);     // JSPの ${isattend} に対応

		req.setAttribute("students", students);













    }
}