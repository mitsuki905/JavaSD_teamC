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
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;
@WebServlet(urlPatterns = { "/student/student_create" })
public class StudentCreateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		session情報を取得
		HttpSession session = req.getSession();
		School school = (School) session.getAttribute("school");

//		Daoを作成
		ClassNumDao numdao = new ClassNumDao();
		StudentDao studao = new StudentDao();
	    //クラスを取得;
	    List<ClassNum> classList = numdao.filter(school);
	    req.setAttribute("classList", classList);

//  	今年の西暦を取得
	    int currentYear = LocalDate.now().getYear();

//		入学年度のリストを格納するための空のリストを作成
	    List<Integer> entYearList = new ArrayList<>();

//		10年前から10年後までの年をリストに追加
	    for (int i = currentYear - 10; i <= currentYear + 10; i++) {
	    	entYearList.add(i);
	    }

//		作成したリストを "entYear" という名前でリクエストにセット
	    req.setAttribute("entYear", entYearList);

//		student_create.jspにフォワード
	    req.getRequestDispatcher("student_create.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
