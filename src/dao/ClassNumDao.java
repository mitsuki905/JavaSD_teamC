package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum; // ClassNum Beanをインポート
import bean.School;

public class ClassNumDao extends DAO {

    /**
     * 指定された学校に所属するクラスの情報をリストで取得します。
     * @param school 検索対象の学校情報
     * @return ClassNumオブジェクトのリスト。見つからない場合は空のリストを返します。
     */
    public List<ClassNum> filter(School school) {
        // 結果を格納するためのリストをClassNum型で作成
        List<ClassNum> list = new ArrayList<>();

        // try-with-resources構文でリソースを自動的にクローズする
        try (Connection con = getConnection()) {
            // テーブル名と列名は実際の設計に合わせてください
            String sql = "SELECT CLASS_NUM FROM CLASS_NUM WHERE SCHOOL_CD = ? ORDER BY CLASS_NUM";

            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, school.getCd());

                try (ResultSet rs = st.executeQuery()) {
                    // 結果セットをループで処理
                    while (rs.next()) {
                        // 1. 新しいClassNumオブジェクトを作成
                        ClassNum classNum = new ClassNum();

                        // 2. ResultSetから取得した値をオブジェクトにセット
                        classNum.setClass_num(rs.getString("CLASS_NUM"));

                        // 3. 引数で受け取ったSchoolオブジェクトもセット
                        classNum.setSchool(school);

                        // 4. リストにオブジェクトを追加
                        list.add(classNum);
                    }
                }
            }
        } catch (Exception e) {
            // 実際にはロギングフレームワークを使うのが望ましい
            e.printStackTrace();
        }

        // 結果のリストを返す
        return list;
    }
}