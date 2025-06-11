package teacher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

/**
 * トップページ（メイン画面）を表示するコントローラ。
 * URLパターン /main にマッピングされている。
 */
@WebServlet(urlPatterns = { "/main" })
public class IndexController extends CommonServlet {

    /**
     * GETメソッドでアクセスされたときの処理。
     * 主にページ初期表示（例：リンククリックやURL直接入力）に使われる。
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // /main/main.jsp というJSPファイルを表示（内部的にフォワード）
        // フォワード：ブラウザのURLは変わらず、サーバー内部でページを切り替える
        req.getRequestDispatcher("/main/main.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // TODO（やること）: 必要に応じて処理を追加する
    }
}
