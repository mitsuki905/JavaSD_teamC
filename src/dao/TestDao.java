/**
 * TestDao.java
 * Testテーブルへのデータアクセスを担当するクラスです。
 * データベースからのデータの取得、登録、更新、削除の機能を提供します。
 * 親クラスであるDAOからデータベース接続機能などを継承しています。
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends DAO {

    /**
     * このDAOで共通して使用するSQLの骨格部分。
     * SELECT句とJOIN句を定義しておくことで、各メソッドでのコードの重複を減らし、
     * テーブル構造の変更があった場合にこの部分のみを修正すれば済むようにしています。
     * カラム名には別名(AS)を付けて、ResultSetから取り出す際のキーを明確にしています。
     */

    private String baseSql = "SELECT "
                           + "  t.point, t.no as test_no, "
                           + "  s.no as student_no, s.name as student_name, s.ent_year, s.class_num, "
                           + "  sub.cd as subject_cd, sub.name as subject_name "
                           + "FROM test t "
                           + "JOIN student s ON t.student_no = s.no AND t.school_cd = s.school_cd "
                           + "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd ";

    /**
     * 主キーに基づいて一件のTestデータを取得します。
     * @param student 学生情報を持つStudentオブジェクト
     * @param subject 科目情報を持つSubjectオブジェクト
     * @param school 学校情報を持つSchoolオブジェクト
     * @param no テストの回数
     * @return 検索結果のTestオブジェクト。見つからない場合はnullを返します。
     */
    public Test get(Student student, Subject subject, School school, int no) {
        Test test = null;
        // baseSqlにWHERE句を追加して、特定の1件を検索するSQLを組み立てる
        String sql = baseSql + "WHERE t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?";

        // try-with-resources文。ブロックを抜ける際に自動でConnectionとPreparedStatementを閉じてくれる
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            // PreparedStatementのプレースホルダ(?)に値をセット
            pstmt.setString(1, student.getNo());
            pstmt.setString(2, subject.getCd());
            pstmt.setString(3, school.getCd());
            pstmt.setInt(4, no);

            // SQLを実行し、結果セットを取得
            try (ResultSet rs = pstmt.executeQuery()) {
                // privateメソッドを呼び出して、結果セットをTestオブジェクトのリストに変換
                List<Test> list = postFilter(rs, school);
                // 結果が1件でもあれば、リストの最初の要素を取得
                if (!list.isEmpty()) {
                    test = list.get(0);
                }
            }
        } catch (Exception e) {
            // 接続エラーやSQLエラーなど、あらゆる例外を捕捉し、実行時例外としてスローする
            // これにより、このメソッドの呼び出し元にエラー発生を通知する
            throw new RuntimeException("Testデータの取得に失敗しました。", e);
        }
        return test;
    }

    /**
     * 複数の条件で絞り込んだTestデータのリストを取得します。
     * 各引数が指定されていない場合(0, null, 空文字など)は、その検索条件を無視します。
     * @param entYear 入学年度。0の場合は無視されます。
     * @param classNum クラス番号。nullまたは空の場合は無視されます。
     * @param subject 科目オブジェクト。nullの場合は無視されます。
     * @param num テスト回数。0の場合は無視されます。
     * @param school 学校オブジェクト。必須の検索条件です。
     * @return 検索結果のTestオブジェクトのリスト。
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) {
        List<Test> list = new ArrayList<>();
        // 動的にSQLを組み立てるためのStringBuilder
        StringBuilder sqlBuilder = new StringBuilder(baseSql);
        // WHERE句の条件と、それに対応するパラメータを格納するリスト
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // 学校は必須条件として最初に追加
        conditions.add("t.school_cd = ?");
        params.add(school.getCd());

        // 各引数が有効な値を持つ場合のみ、条件とパラメータを追加
        if (entYear > 0) { conditions.add("s.ent_year = ?"); params.add(entYear); }
        if (classNum != null && !classNum.isEmpty() && !classNum.equals("0")) { conditions.add("s.class_num = ?"); params.add(classNum); }
        if (subject != null) { conditions.add("t.subject_cd = ?"); params.add(subject.getCd()); }
        if (num > 0) { conditions.add("t.no = ?"); params.add(num); }

        // 条件が1つ以上あれば、WHERE句を組み立てる
        if (conditions.size() > 1) { // school_cdは必ずあるので1より大きいかで判断
            sqlBuilder.append("WHERE ");
            sqlBuilder.append(String.join(" AND ", conditions)); // "cond1 AND cond2 AND ..." のように結合
        }
        // 最後に並び順を指定
        sqlBuilder.append(" ORDER BY s.ent_year, s.class_num, s.no, t.subject_cd");

        String sql = sqlBuilder.toString();

        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            // パラメータリストから順番に値をセット
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                list = postFilter(rs, school);
            }
        } catch (Exception e) {
            throw new RuntimeException("Testデータのフィルタリングに失敗しました。", e);
        }
        return list;
    }

    /**
     * 複数のTestオブジェクトをまとめてデータベースに保存（登録または更新）します。
     * トランザクション処理を行い、全てのデータが正常に保存できた場合のみ処理を確定(commit)します。
     * @param list 保存するTestオブジェクトのリスト
     * @return 全ての保存が成功した場合はtrue、一件でも失敗した場合はfalse
     */
    public boolean save(List<Test> list) {
        // 保存対象がなければ何もしないで成功を返す
        if (list == null || list.isEmpty()) return true;

        Connection con = null;
        try {
            con = getConnection();
            // トランザクションを開始するために、自動コミットを無効にする
            con.setAutoCommit(false);

            // リスト内のTestオブジェクトを一つずつ保存
            for (Test test : list) {
                // privateなsaveメソッドを呼び出す
                if (!save(test, con)) {
                    // 1件でも失敗したら、ここまでの処理を全て取り消す(rollback)
                    con.rollback();
                    return false;
                }
            }
            // 全てのループが正常に完了したら、変更をデータベースに確定する(commit)
            con.commit();
            return true;
        } catch (Exception e) {
            // 例外が発生した場合、変更をロールバックする
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rbex) {
                    // ロールバック自体が失敗した場合は、その情報を元の例外に追加する
                    e.addSuppressed(rbex);
                }
            }
            throw new RuntimeException("Testデータの保存に失敗しました。", e);
        } finally {
            // 処理が成功しようが失敗しようが、最後に必ずデータベース接続を閉じる
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // close時のエラーは、ログ出力などに留め、上位の処理には影響させない
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 複数のTestオブジェクトをまとめてデータベースから削除します。
     * トランザクション処理を行い、全てのデータが正常に削除できた場合のみ処理を確定(commit)します。
     * @param list 削除するTestオブジェクトのリスト
     * @return 全ての削除が成功した場合はtrue、一件でも失敗した場合はfalse
     */
    public boolean delete(List<Test> list) {
        // saveメソッドとほぼ同じ構造
        if (list == null || list.isEmpty()) return true;
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            for (Test test : list) {
                if (!delete(test, con)) {
                    con.rollback();
                    return false;
                }
            }
            con.commit();
            return true;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rbex) {
                    e.addSuppressed(rbex);
                }
            }
            throw new RuntimeException("Testデータの削除に失敗しました。", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ------------------- private メソッド -------------------
    // このクラスの内部でのみ使用される補助的なメソッド群

    /**
     * [private] ResultSetの検索結果をTestオブジェクトのリストに変換するヘルパーメソッドです。
     * getやfilterメソッドから呼び出され、オブジェクトへのデータマッピング処理を共通化します。
     * @param rs データベースからの検索結果セット
     * @param school 学校情報。JOINで取得する代わりに引数で受け取ることで効率化
     * @return Testオブジェクトのリスト
     * @throws SQLException ResultSetの操作中に発生する可能性のある例外
     */
    private List<Test> postFilter(ResultSet rs, School school) throws SQLException {
        List<Test> list = new ArrayList<>();
        // 結果セットを一行ずつループ処理
        while (rs.next()) {
            // 各行のデータを格納するためのBeanオブジェクトを生成
            Test test = new Test();
            Student student = new Student();
            Subject subject = new Subject();

            // Studentオブジェクトにデータをセット
            student.setNo(rs.getString("student_no"));
            student.setName(rs.getString("student_name"));
            student.setEntYear(rs.getInt("ent_year"));
            student.setSchool(school);

            String classNumStr = rs.getString("class_num");
            if (classNumStr != null && !classNumStr.isEmpty()) {
                student.setClassNum(classNumStr);
                test.setClassNum(classNumStr);
            }

            // Subjectオブジェクトにデータをセット
            subject.setCd(rs.getString("subject_cd"));
            subject.setName(rs.getString("subject_name"));
            subject.setSchool(school);

            // Testオブジェクトにデータをセット
            test.setPoint(rs.getInt("point"));
            test.setNo(rs.getInt("test_no"));
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);

            // 完成したTestオブジェクトをリストに追加
            list.add(test);
        }
        return list;
    }

    /**
     * [private] 1件のTestオブジェクトをデータベースに保存（登録または更新）するヘルパーメソッドです。
     * @param test 保存するTestオブジェクト
     * @param connection トランザクション管理下にあるデータベース接続
     * @return 処理が成功した場合はtrue
     * @throws SQLException SQLの実行中に発生する可能性のある例外
     */
    private boolean save(Test test, Connection connection) throws SQLException {
        // H2 Databaseで動作するMERGE文に修正
        // 主キー(student_no, subject_cd, school_cd, no)が一致する行があればUPDATE、なければINSERT
        String sql = "MERGE INTO test (student_no, subject_cd, school_cd, no, class_num, point) "
                   + "KEY(student_no, subject_cd, school_cd, no) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, test.getStudent().getNo());
            pstmt.setString(2, test.getSubject().getCd());
            pstmt.setString(3, test.getSchool().getCd());
            pstmt.setInt(4, test.getNo());
            pstmt.setString(5, test.getClassNum());
            pstmt.setInt(6, test.getPoint());

            // executeUpdate()は変更された行数を返す
            int affectedRows = pstmt.executeUpdate();
            // 1行以上変更されていれば成功
            return affectedRows > 0;
        }
    }

    /**
     * [private] 1件のTestオブジェクトをデータベースから削除するヘルパーメソッドです。
     * @param test 削除するTestオブジェクト
     * @param connection トランザクション管理下にあるデータベース接続
     * @return 処理が成功した場合はtrue
     * @throws SQLException SQLの実行中に発生する可能性のある例外
     */
    private boolean delete(Test test, Connection connection) throws SQLException {
        String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, test.getStudent().getNo());
            pstmt.setString(2, test.getSubject().getCd());
            pstmt.setString(3, test.getSchool().getCd());
            pstmt.setInt(4, test.getNo());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}