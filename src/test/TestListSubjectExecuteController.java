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
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/test/test_list_subject" })
public class TestListSubjectExecuteController extends CommonServlet {

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

        String entYearStr = req.getParameter("f1");
        String subjectCd = req.getParameter("f2");
        String classNum = req.getParameter("f3");

        // バリデーション
        if (entYearStr == null || entYearStr.equals("0") || classNum == null || classNum.equals("0") || subjectCd == null || subjectCd.equals("0")) {
            req.setAttribute("error_subject", "入学年度とクラスと科目を選択してください");
            // バリデーションエラー時は初期表示画面に戻す
            setSearchOptions(req, school);
            req.setAttribute("fEntYear", entYearStr);
            req.setAttribute("fClassNum", classNum);
            req.setAttribute("fSubjectCd", subjectCd);
            req.getRequestDispatcher("/test/test_list.jsp").forward(req, resp);
            return;
        }

        TestListSubjectDao testDao = new TestListSubjectDao();
        SubjectDao subjectDao = new SubjectDao();

        try {
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = subjectDao.get(subjectCd, school);
            List<TestListSubject> list = testDao.filter(entYear, classNum, subject, school);

            req.setAttribute("students", list);
            req.setAttribute("subjectName", subject.getName());

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error_message", "検索中にエラーが発生しました。");
        } finally {
            // 成功時も例外発生時も、必ず検索オプションと入力値をセットする
            setSearchOptions(req, school);
            req.setAttribute("fEntYear", entYearStr);
            req.setAttribute("fClassNum", classNum);
            req.setAttribute("fSubjectCd", subjectCd);
        }

        // 結果表示画面にフォワード
        req.getRequestDispatcher("/test/test_list_subject.jsp").forward(req, resp);
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