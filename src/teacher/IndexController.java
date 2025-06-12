package teacher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

/**
 * トップページ（メイン画面）を表示するコントローラ。
 * URLパターン /main にマッピングされている。
 */
@WebServlet(urlPatterns = { "/main/main" })
public class IndexController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("/main/main.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // TODO（やること）: 必要に応じて処理を追加する
    }
}
