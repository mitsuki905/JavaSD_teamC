package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.CommonServlet;
@WebServlet(urlPatterns = { "/student/student_update" })
public class StudentUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

//		画面から送られてきた値を取得する
		int entYear = Integer.parseInt(req.getParameter("entYear"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("classNum");

		req.setAttribute("entYear", entYear);
		req.setAttribute("no", no);
		req.setAttribute("name", name);
		req.setAttribute("classNum", classNum);


		StudentDao studao = new StudentDao();

        try {
            // 学生リストを取得
            List<Student> studentList = studao.getList();

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


		req.getRequestDispatcher("student_update.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
