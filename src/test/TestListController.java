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
import bean.Teacher;
import dao.StudentDao;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/test/test_list" })
public class TestListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        School school = teacher.getSchool();

        try {
            // ドロップダウンリスト用のデータをリクエストスコープに設定
            setSearchOptions(request, school);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error_message", "データの取得中にエラーが発生しました。");
        }

        // 検索フォーム専用のJSPにフォワード
        request.getRequestDispatcher("/test/test_list.jsp").forward(request, response);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        get(req, resp);
    }

    // ドロップダウンリストのデータをセットするヘルパーメソッド
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

        List<Subject> subjects = subDao.filter(school);
        request.setAttribute("subjectList", subjects);
    }
}