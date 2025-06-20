package student;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import dao.ClassNumDao;
import tool.CommonServlet;
@WebServlet(urlPatterns = { "/student/student_update" })
public class StudentUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		画面から送られてきた値を取得する
		int entYear = Integer.parseInt(req.getParameter("entYear"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("classNum");

		req.setAttribute("entYear", entYear);
		req.setAttribute("no", no);
		req.setAttribute("name", name);
		req.setAttribute("classNum", classNum);



		ClassNumDao numdao = new ClassNumDao();


        try {
            // 学生リストを取得;
            List<ClassNum> classList = numdao.filter(school);


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
