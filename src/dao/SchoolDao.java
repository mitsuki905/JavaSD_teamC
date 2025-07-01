package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class SchoolDao extends DAO {

    /**
     * 指定された学校コードに一致する学校情報を取得する
     *
     * @param cd 学校コード
     * @return 見つかった場合はSchoolオブジェクト、見つからない場合やエラー発生時はnullを返します。
     */
    public School get(String cd) {
        School school = null;
        // try-with-resources構文を使用
        // これにより、con, st, rs がブロックを抜ける際に自動的に close() される
        try (Connection con = getConnection()) {
            // 必要な列を明示的に指定する
            String sql = "SELECT cd, name FROM SCHOOL WHERE CD = ?";

            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, cd); // 引数名を cd に変更

                try (ResultSet rs = st.executeQuery()) {
                    // データが存在すればSchoolオブジェクトを生成して値をセット
                    if (rs.next()) {
                        school = new School();
                        school.setCd(rs.getString("cd"));
                        school.setName(rs.getString("name"));
                    }
                }
            }
        } catch (Exception e) {
            // エラーが発生した場合は、ログに出力する
            e.printStackTrace();

            // エラーが発生したことを明確にするため、schoolはnullのままになる
        }

        // schoolオブジェクト（見つからない場合はnull）を返す
        return school;
    }
}