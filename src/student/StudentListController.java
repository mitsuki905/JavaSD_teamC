package student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/student/student_list" })
public class StudentListController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, Exception {
        post(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        School school = (School) session.getAttribute("school");
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        List<Student> students = new ArrayList<>();
        int fEntYear = 0;
        String fClassNum = "0";
        boolean isAttend = false;

        String entYearStr = req.getParameter("f_ent_year");
        boolean isSearching = (entYearStr != null);

        if (isSearching) {
            String classNumStr = req.getParameter("f2");
            String isAttendStr = req.getParameter("f3");

            if (entYearStr != null && !entYearStr.isEmpty()) {
                fEntYear = Integer.parseInt(entYearStr);
            }
            if (classNumStr != null && !classNumStr.isEmpty()) {
                fClassNum = classNumStr;
            }
            isAttend = (isAttendStr != null);

            // ★★★★★ ここからロジックを修正 ★★★★★

            if (fEntYear != 0) {
                // 入学年度が指定されている場合 (クラス指定の有無で分岐)
                if (!fClassNum.equals("0")) {
                    // 入学年度とクラスの両方を指定
                    // 呼び出し: filter(School, int, String, boolean) -> これはDAOに存在する
                    students = studentDao.filter(school, fEntYear, fClassNum, isAttend);
                } else {
                    // 入学年度のみ指定
                    // 呼び出し: filter(School, int, boolean) -> これはDAOに存在する
                    students = studentDao.filter(school, fEntYear, isAttend);
                }
            } else {
                // 入学年度が指定されていない場合
                // この場合、クラスが指定されていても無視し、在学フラグのみで絞り込む
                // これにより、存在しないメソッドを呼び出すことを回避する

                // (もしクラスのみで絞り込んだ場合にエラーメッセージを出したいならここに記述)
                // if (!fClassNum.equals("0")) {
                //     req.setAttribute("error", "クラスで絞り込む場合は、入学年度も選択してください。");
                // }

                // 呼び出し: filter(School, boolean) -> これはDAOに存在する
                students = studentDao.filter(school, isAttend);
            }
            // ★★★★★ 修正はここまで ★★★★★
        }

        List<Integer> yearList = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            yearList.add(i);
        }
        List<ClassNum> classList = classNumDao.filter(school);

        req.setAttribute("yearList", yearList);
        req.setAttribute("classList", classList);
        req.setAttribute("students", students);
        req.setAttribute("fEntYear", fEntYear);
        req.setAttribute("fClassNum", fClassNum);
        req.setAttribute("isattend", isAttend);

        req.getRequestDispatcher("/student/student_list.jsp").forward(req, resp);
    }
}