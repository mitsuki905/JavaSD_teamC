package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}