/**
 * TestListStudentDao.java
 * 特定の学生の成績一覧を取得するためのデータアクセスオブジェクト(DAO)です。
 * 学生一人を軸として、その学生が受けた全科目のテスト結果をリスト形式で返します。
 * 主に学生個人の成績詳細ページなどで使用されます。
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 関連するBeanクラスをインポート
import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends DAO {

    /**
     * このDAOで使用するSQLの骨格部分。
     * testテーブルとsubjectテーブルをJOINして、科目名(subject_name)を取得できるようにしています。
     * WHERE句以降はfilterメソッド内で動的に追加します。
     */
    private String baseSql = "SELECT t.subject_cd, sub.name as subject_name, t.no, t.point "
                           + "FROM test t "
                           + "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd ";

    /**
     * 特定の学生の成績一覧を取得します。
     * @param student 成績を取得したい学生のStudentオブジェクト。学生番号(no)と学校(school)の情報が使われます。
     * @return その学生の成績一覧(TestListStudentのリスト)。データがない場合は空のリストを返します。
     */
    public List<TestListStudent> filter(Student student) {
        // 結果を格納するための空のリストを初期化
        List<TestListStudent> list = new ArrayList<>();

        // baseSqlに特定の学生を絞り込むWHERE句と、並び順を指定するORDER BY句を追加
        String sql = baseSql + "WHERE t.student_no = ? AND t.school_cd = ? ORDER BY t.subject_cd, t.no";

        // try-with-resources文で、ConnectionとPreparedStatementが自動的にクローズされるようにする
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // SQL文のプレースホルダ(?)に値をセットする
            // 1番目の?に学生番号をセット
            pstmt.setString(1, student.getNo());
            // 2番目の?に学校コードをセット (Studentオブジェクトが持つSchoolオブジェクトから取得)
            pstmt.setString(2, student.getSchool().getCd());

            // SQLを実行し、結果セット(ResultSet)を取得
            try (ResultSet rs = pstmt.executeQuery()) {
                // privateな補助メソッドを呼び出して、結果セットをオブジェクトのリストに変換
                list = this.postFilter(rs);
            }
        } catch (Exception e) {
            // データベース接続エラーやSQL実行エラーなど、あらゆる例外を捕捉
            // 捕捉した例外を、より分かりやすいメッセージを持つRuntimeExceptionでラップしてスローする
            // これにより、呼び出し元(サーブレットなど)でエラーを一元管理できる
            throw new RuntimeException("成績一覧の取得に失敗しました。", e);
        }

        // 変換されたリストを返す
        return list;
    }

    /**
     * [private] ResultSetをTestListStudentのリストに変換するヘルパーメソッドです。
     * このクラスの内部でのみ使用されます。
     * @param rs データベースからの検索結果セット
     * @return TestListStudentオブジェクトのリスト
     * @throws SQLException ResultSetの操作中に発生する可能性のある例外
     */
    private List<TestListStudent> postFilter(ResultSet rs) throws SQLException {
        // 結果を格納するための空のリストを初期化
        List<TestListStudent> list = new ArrayList<>();

        // 結果セットに次の行が存在する間、ループを続ける
        while (rs.next()) {
            // 1行分のデータを入れるための新しいBeanオブジェクト(TestListStudent)を生成
            TestListStudent testListStudent = new TestListStudent();

            // ResultSetから各カラムの値を、別名をキーにして取得し、Beanの各フィールドにセットしていく
            testListStudent.setSubjectCd(rs.getString("subject_cd"));
            testListStudent.setSubjectName(rs.getString("subject_name"));
            testListStudent.setNum(rs.getInt("no"));
            testListStudent.setPoint(rs.getInt("point"));

            // 完成したBeanをリストに追加する
            list.add(testListStudent);
        }

        // 全ての行の処理が終わったら、完成したリストを返す
        return list;
    }
}