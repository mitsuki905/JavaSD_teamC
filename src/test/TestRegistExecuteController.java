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

    /**
     * GETリクエストは不正アクセスとみなし、成績登録画面にリダイレクト
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect(req.getContextPath() + "/test/test_regist");
    }

    /**
     * POSTリクエスト: 成績をDBに登録する
     */
    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        // ログインチェック
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

        int num = 0;
        int entYear = 0;

        try {
            num = Integer.parseInt(numStr);
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "入学年度、クラス、科目、回数をすべて選択してください");
            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
            return;
        }

        // 科目オブジェクトを取得
        Subject subject = subDao.get(subjectCd, school);

        // バリデーション用Mapと登録用List
        Map<String, String> errors = new HashMap<>();
        Map<String, String> points = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        if (studentNos != null) {
            for (String studentNo : studentNos) {
                String pointStr = req.getParameter("point_" + studentNo);
                points.put(studentNo, pointStr); // エラー時の再表示のため入力値を保持

                // 点数が入力されている場合のみバリデーションと登録対象にする
                if (pointStr != null && !pointStr.isEmpty()) {
                    try {
                        int point = Integer.parseInt(pointStr);
                        if (point < 0 || point > 100) {
                            errors.put(studentNo, "0～100の整数を入力してください");
                        } else {
                            // バリデーションOKならTestオブジェクトを作成
                            Student student = sDao.get(studentNo);
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

        // バリデーションエラーがある場合
        if (!errors.isEmpty()) {
            // エラーメッセージと入力値をリクエストスコープにセット
            req.setAttribute("errors", errors);
            req.setAttribute("points", points);

            // --- 画面再表示のためのデータ準備 ---
            List<Student> students = sDao.filter(school, entYear, classNum, true);
            req.setAttribute("students", students);
            req.setAttribute("subject", subject);
            req.setAttribute("num", num);

            // 検索フォームの選択状態を復元
            req.setAttribute("f_ent_year", entYear);
            req.setAttribute("f_class_num", classNum);
            req.setAttribute("f_subject_cd", subjectCd);
            req.setAttribute("f_num", num);

            // 登録処理に必要な情報も再度セット
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);

            // プルダウン用のデータを再設定
            setRequestAttributesForForm(req, school);

            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
            return;
        }

        // エラーがない場合、DBに保存
        boolean result = tDao.save(testList);

        if (result) {
            // 成功したら完了画面へフォワード
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, resp);
        } else {
            // 保存失敗時（通常はDBエラーなど）
            req.setAttribute("error", "データベースエラーが発生しました。");
            // 画面を再表示
            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
        }
    }

    /**
     * エラー発生時に画面を再表示するため、フォームに必要なデータを再度設定するヘルパーメソッド
     */
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