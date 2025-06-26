package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/test/test_regist_execute" })
public class TestRegistExecuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect(req.getContextPath() + "/test/test_regist");
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/login/login.jsp");
            return;
        }

        School school = teacher.getSchool();

        // DAOのインスタンス化
        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        TestDao tDao = new TestDao();

        // リクエストパラメータの取得
        String[] studentNos = req.getParameterValues("student_no");
        String subjectCd = req.getParameter("subject_cd");
        String numStr = req.getParameter("num");
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
     // どのボタンが押されたかを取得
        String submitAction = req.getParameter("submit_action");

        int num = 0;
        int entYear = 0;
        try {
            num = Integer.parseInt(numStr);
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "不正なリクエストです。");
            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
            return;
        }

        // 科目オブジェクトを取得
        Subject subject = subDao.get(subjectCd, school);

        // 1. 必要な学生情報をDBから一度にまとめて取得する
        List<Student> students = sDao.filter(school, entYear, classNum, true);

        // 2. 学生番号をキーにしたMapに変換し、ループ内で高速に検索できるようにする
        Map<String, Student> studentMap = students.stream()
            .collect(Collectors.toMap(Student::getNo, s -> s));

        // バリデーション用Mapと登録用List
        Map<String, String> errors = new HashMap<>();
        Map<String, String> points = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        if (studentNos != null) {
            for (String studentNo : studentNos) {
                String pointStr = req.getParameter("point_" + studentNo);
                points.put(studentNo, pointStr);

                if (pointStr != null && !pointStr.isEmpty()) {
                    try {
                        int point = Integer.parseInt(pointStr);
                        if (point < 0 || point > 100) {
                            errors.put(studentNo, "0～100の整数を入力してください");
                        } else {
                            // 3. DBアクセスではなく、メモリ上のMapから学生情報を取得する
                            Student student = studentMap.get(studentNo);

                            if (student != null) {
                                Test test = new Test();
                                test.setStudent(student);
                                test.setSubject(subject);
                                test.setSchool(school);
                                test.setNo(num);
                                test.setClassNum(student.getClassNum());
                                test.setPoint(point);
                                testList.add(test);
                            }
                        }
                    } catch (NumberFormatException e) {
                        errors.put(studentNo, "整数を入力してください");
                    }
                }
            }
        }

        // エラーがない場合、DBに保存
        boolean result = tDao.save(testList);

        if (!result) {
            req.setAttribute("error", "データベースエラーが発生しました。");
            // 元の検索画面にフォワード
            req.getRequestDispatcher("test_regist").forward(req, resp);
            return;
        }

        // 押されたボタンに応じて遷移先を分岐
        if ("register_finish".equals(submitAction)) {
            // 「登録して終了」の場合 -> 完了画面へ
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, resp);
        } else if ("register_again".equals(submitAction)) {
            // 「登録して再度入力」の場合 -> 再検索して成績登録画面へ
            // POSTリクエストとしてTestRegistControllerにフォワードすることで、再検索を実行させる
        	req.setAttribute("rechance", "登録は完了しました");

        	// 検索条件のセット
            req.setAttribute("f_ent_year", entYear);
            req.setAttribute("f_class_num", classNum);
            req.setAttribute("f_subject_cd", subjectCd);
            req.setAttribute("f_num", num);

            // 検索用hiddenにも同じ値をセットしておく
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);
            req.setAttribute("subject_cd", subjectCd);
            req.setAttribute("num", num);

            req.getRequestDispatcher("test_regist").forward(req, resp);
        } else {
            // 想定外のアクションの場合
            req.setAttribute("error", "不正な操作が行われました。");
            req.getRequestDispatcher("test_regist").forward(req, resp);
        }
    }
}