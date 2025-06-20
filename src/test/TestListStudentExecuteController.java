package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/test/test_list_student" })
public class TestListStudentExecuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect(req.getContextPath() + "/test/test_list");
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        School school = teacher.getSchool();
        String studentNo = req.getParameter("f4");

        if (studentNo == null || studentNo.trim().isEmpty()) {
            // エラーメッセージをリクエストスコープに設定
            req.setAttribute("error_student", "学生番号を入力してください");

            // ドロップダウンリストのデータを再設定
            setSearchOptions(req, school);

            // 入力された値を保持（この場合は空ですが、他のフォームとの整合性のため）
            req.setAttribute("no", studentNo);

            // 初期表示画面にフォワードしてエラーメッセージを表示
            req.getRequestDispatcher("/test/test_list.jsp").forward(req, resp);
            return; // 処理をここで中断
        }


        StudentDao studentDao = new StudentDao();
        TestListStudentDao testDao = new TestListStudentDao();

        try {
            Student student = studentDao.get(studentNo);

            if (student != null) {
                List<TestListStudent> list = testDao.filter(student);
                req.setAttribute("tests", list);
            }
            req.setAttribute("student", student);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error_message", "検索中にエラーが発生しました。");
        } finally {
            setSearchOptions(req, school);
            req.setAttribute("no", studentNo);
        }

        req.getRequestDispatcher("/test/test_list_student.jsp").forward(req, resp);
    }

    private void setSearchOptions(HttpServletRequest request, School school) throws Exception {
        StudentDao stuDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        List<Student> students = stuDao.filter(school, true);
        Set<Integer> yearSet = new TreeSet<>();
        students.forEach(s -> yearSet.add(s.getEntYear()));
        request.setAttribute("yearList", new ArrayList<>(yearSet));
        Set<String> classSet = new TreeSet<>();
        students.forEach(s -> classSet.add(s.getClassNum()));
        request.setAttribute("classList", new ArrayList<>(classSet));
        request.setAttribute("subjectList", subDao.filter(school));
    }
}