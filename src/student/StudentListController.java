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
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, Exception {

        // --- 1. 準備 (常に実行) ---
        HttpSession session = req.getSession();
        School school = (School) session.getAttribute("school");

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        // --- 2. 変数の初期化 ---
        // ★★★★★ 最初は空のリストを用意する ★★★★★
        List<Student> students = new ArrayList<>();

        int fEntYear = 0;
        String fClassNum = "0";
        boolean isAttend = false; // デフォルトはfalseにしておく

        // --- 3. 検索が実行されたかどうかを判断 ---
        String entYearStr = req.getParameter("f_ent_year");

        // ★★★★★ f_ent_yearパラメータがあれば、検索が実行されたとみなす ★★★★★
        boolean isSearching = (entYearStr != null);

        // --- 4. 検索が実行された場合のみ、DBに問い合わせる ---
        if (isSearching) {
            // リクエストからパラメータを取得
            String classNumStr = req.getParameter("f_class_num");
            String isAttendStr = req.getParameter("is_attend");

            if (!entYearStr.isEmpty()) {
                fEntYear = Integer.parseInt(entYearStr);
            }
            if (classNumStr != null && !classNumStr.isEmpty()) {
                fClassNum = classNumStr;
            }
            isAttend = (isAttendStr != null); // チェックボックスがONなら"true"、OFFならnull

            // 条件に応じてDAOを呼び出し、studentsリストを上書きする
            if (fEntYear != 0 && !fClassNum.equals("0")) {
                students = studentDao.filter(school, fEntYear, fClassNum, isAttend);
            } else if (fEntYear != 0) {
                students = studentDao.filter(school, fEntYear, isAttend);
            } else {
                students = studentDao.filter(school, isAttend);
            }
        }

        // --- 5. JSPに渡すデータをセット (常に実行) ---
        // ドロップダウンリスト用のデータは常に必要
        List<Integer> yearList = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            yearList.add(i);
        }
        List<ClassNum> classList = classNumDao.filter(school);

        req.setAttribute("yearList", yearList);
        req.setAttribute("classList", classList);

        // 検索結果（空 or データ入り）をセット
        req.setAttribute("students", students);

        // 検索条件をフォームに維持するために値をセット
        req.setAttribute("fEntYear", fEntYear);
        req.setAttribute("fClassNum", fClassNum);
        req.setAttribute("isAttend", isAttend);

        // --- 6. JSPへフォワード ---
        req.getRequestDispatcher("/student/student_list.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {


    }
}