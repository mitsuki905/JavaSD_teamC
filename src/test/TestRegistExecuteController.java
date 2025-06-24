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
import dao.ClassNumDao;
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

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("points", points);
            // 画面再表示のため、最初に取得した学生リストをセット
            req.setAttribute("students", students);
            req.setAttribute("subject", subject);
            req.setAttribute("num", num);
            req.setAttribute("f_ent_year", entYear);
            req.setAttribute("f_class_num", classNum);
            req.setAttribute("f_subject_cd", subjectCd);
            req.setAttribute("f_num", num);
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);
            setRequestAttributesForForm(req, school);
            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
            return;
        }

     // エラーがない場合、DBに保存
        boolean result = tDao.save(testList);

        if (!result) {
            req.setAttribute("error", "データベースエラーが発生しました。");
            req.getRequestDispatcher("test_regist").forward(req, resp);
            return;
        }

        // ボタンの種類で分岐（result が true の場合のみ）
        if ("register_finish".equals(submitAction)) {
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, resp);
        } else if ("register_again".equals(submitAction)) {
            // 成功後、再度同じ条件で検索して表示
            List<Student> student = sDao.filter(school, entYear, classNum, true);
            req.setAttribute("rechance", "登録は完了しました");
            req.setAttribute("students", student);
            req.setAttribute("subject", subject);
            req.setAttribute("num", num);
            req.setAttribute("f_ent_year", entYear);
            req.setAttribute("f_class_num", classNum);
            req.setAttribute("f_subject_cd", subjectCd);
            req.setAttribute("f_num", num);
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);
            setRequestAttributesForForm(req, school);
            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "不正な操作が行われました。");
            req.getRequestDispatcher("test_regist").forward(req, resp);
        }

    }

    private void setRequestAttributesForForm(HttpServletRequest req, School school) throws Exception {
        StudentDao sDao = new StudentDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();

        List<Integer> entYearSet = sDao.filter(school, true).stream()
            .map(Student::getEntYear)
            .distinct()
            .sorted((y1, y2) -> y2.compareTo(y1))
            .collect(Collectors.toList());
        req.setAttribute("entYearSet", entYearSet);

        List<String> classList = cDao.filter(school).stream()
            .map(cn -> cn.getClass_num())
            .sorted()
            .collect(Collectors.toList());
        req.setAttribute("classList", classList);

        List<Subject> subjectList = subDao.filter(school);
        req.setAttribute("subjectList", subjectList);
    }
}