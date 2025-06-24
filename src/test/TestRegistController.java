package test;

import java.util.List;
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

@WebServlet(urlPatterns = { "/test/test_regist" })
public class TestRegistController extends CommonServlet {

    /**
     * フォームに必要なプルダウンリストをリクエストスコープに設定するメソッド
     * @param req HttpServletRequest
     * @param school Schoolオブジェクト
     * @throws Exception
     */
    private void setRequestAttributesForForm(HttpServletRequest req, School school) throws Exception {
        StudentDao sDao = new StudentDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();

        // StudentDAOのfilter(school, true)で在校生リストを取得し、入学年度のリストを作成
        List<Integer> entYearSet = sDao.filter(school, true).stream()
                .map(Student::getEntYear)
                .distinct()
                .sorted((y1, y2) -> y2.compareTo(y1)) // 降順にソート
                .collect(Collectors.toList());
        req.setAttribute("entYearSet", entYearSet);

        // クラス番号のリストを取得 (ClassNumオブジェクトのリストをStringのリストに変換)
        List<String> classList = cDao.filter(school).stream()
                .map(cn -> cn.getClass_num()) // ClassNum Beanのgetterを呼び出す
                .sorted()
                .collect(Collectors.toList());
        req.setAttribute("classList", classList);

        // 科目のリストを取得
        List<Subject> subjectList = subDao.filter(school);
        req.setAttribute("subjectList", subjectList);
    }

    /**
     * GETリクエスト: 成績登録画面を初期表示する
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        // ログインチェック
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/login/login.jsp");
            return;
        }

        // フォーム用のデータをセットしてJSPにフォワード
        setRequestAttributesForForm(req, teacher.getSchool());
        req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
    }

    /**
     * POSTリクエスト: 学生を検索し、結果を表示する
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

        // JSPにフォワードする前に、常にフォーム用データをセットしておく
        setRequestAttributesForForm(req, school);

        // リクエストパラメータの取得
        String entYearStr = req.getParameter("f_ent_year");
        String classNum = req.getParameter("f_class_num");
        String subjectCd = req.getParameter("f_subject_cd");
        String numStr = req.getParameter("f_num");

        int entYear = 0;
        int num = 0;

        try {
            entYear = Integer.parseInt(entYearStr);
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
             // "--------"が選択された場合(値が0)はNumberFormatExceptionは発生しない
        }

        // 検索条件をリクエストスコープにセット（フォームの選択状態維持のため）
        req.setAttribute("f_ent_year", entYear);
        req.setAttribute("f_class_num", classNum);
        req.setAttribute("f_subject_cd", subjectCd);
        req.setAttribute("f_num", num);

        // DAOのインスタンス化
        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        TestDao tDao = new TestDao();


//         入学年度、クラス、科目がすべて選択されている場合のみ検索
        if (entYear > 0 && classNum != null && !classNum.equals("0") && subjectCd != null && !subjectCd.equals("0") && num > 0) {
//          在学中の学生リストを取得
            List<Student> students = sDao.filter(school, entYear, classNum, true);
//          選択された科目情報を取得
            Subject subject = subDao.get(subjectCd, school);
//          既に登録されているテスト結果を取得（点数用）
            List<Test> testList  = tDao.filter(entYear, classNum, subject, num, school);


//          検索結果と選択された情報をリクエストスコープにセット
            req.setAttribute("students", students);
            req.setAttribute("subject", subject);
            req.setAttribute("num", num);
            req.setAttribute("testList", testList);

//          登録処理で使うため、元の検索条件もセット
            req.setAttribute("ent_year", entYear);
            req.setAttribute("class_num", classNum);


        } else {
            // 検索条件が不完全な場合のエラーメッセージ
            req.setAttribute("error", "入学年度、クラス、科目、回数をすべて選択してください");
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, resp);
    }
}