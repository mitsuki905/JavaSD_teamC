package test;

import java.util.ArrayList;
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

/**
 * 成績データを削除するためのサーブレット。
 * test_regist.jspの削除チェックボックスが選択された際に呼び出されます。
 */
@WebServlet(urlPatterns = { "/test/test_delete_execute" })
public class TestDeleteExecuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // GETリクエストは不正なアクセスとみなし、成績登録の初期画面にリダイレクト
        resp.sendRedirect(req.getContextPath() + "/test/test_regist");
    }

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

        // フォームから送信されたパラメータを取得
        // 削除対象の学生番号（チェックボックスで選択されたもの）
        String[] deleteStudentNos = req.getParameterValues("delete_student_no");
        String subjectCd = req.getParameter("subject_cd");
        String numStr = req.getParameter("num");
        // 「削除して再度入力」のために検索条件も取得
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        // どのボタンが押されたかを取得
        String submitAction = req.getParameter("submit_action");

        int num = 0;
        int entYear = 0;

        try {
            // 文字列を数値に変換
            num = Integer.parseInt(numStr);
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            // 通常は発生しないが、不正なリクエストに対する防御
            req.setAttribute("error", "不正なリクエストです。");
            req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
            return;
        }

        // 科目オブジェクトを取得
        Subject subject = subDao.get(subjectCd, school);

        // 削除対象の成績リストを作成
        List<Test> deleteList = new ArrayList<>();

        if (deleteStudentNos != null) {
            // 学生情報をまとめて取得（パフォーマンスのため）
            Map<String, Student> studentMap = sDao.filter(school, entYear, classNum, true).stream()
                .collect(Collectors.toMap(Student::getNo, s -> s));

            for (String studentNo : deleteStudentNos) {
                // Mapから学生情報を取得
                Student student = studentMap.get(studentNo);
                if (student != null) {
                    Test test = new Test();
                    test.setStudent(student);
                    test.setSubject(subject);
                    test.setSchool(school);
                    test.setNo(num);
                    // Testテーブルにはクラス番号も記録されているためセット
                    test.setClassNum(student.getClassNum());
                    deleteList.add(test);
                }
            }
        }

        // データベースから削除を実行
        boolean result = tDao.delete(deleteList);

        if (!result) {
            // 削除に失敗した場合
            req.setAttribute("error", "データベースエラーが発生し、削除に失敗しました。");
            // 元の検索画面にフォワード
            req.getRequestDispatcher("test_regist").forward(req, resp);
            return;
        }

        // 押されたボタンに応じて遷移先を分岐
        if ("delete_finish".equals(submitAction)) {
            // 「削除して終了」の場合 -> 完了画面へ
            req.getRequestDispatcher("test_delete_done").forward(req, resp);
        } else if ("delete_again".equals(submitAction)) {
            // 「削除して再度入力」の場合 -> 再検索して成績登録画面へ
            // POSTリクエストとしてTestRegistControllerにフォワードすることで、再検索を実行させる
        	req.setAttribute("rechance", "削除は完了しました");
            req.getRequestDispatcher("test_regist").forward(req, resp);
        } else {
            // 想定外のアクションの場合
            req.setAttribute("error", "不正な操作が行われました。");
            req.getRequestDispatcher("test_regist").forward(req, resp);
        }
    }
}