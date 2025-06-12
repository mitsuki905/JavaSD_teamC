package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 成績一覧（科目別）の学生一人分のデータを格納するBeanクラス。
 * このクラスはUMLクラス図に基づいて生成されました。
 */
public class TestListSubject implements Serializable {

    // --- フィールド ---

    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    // Map<テスト回数, 点数>
    private Map<Integer, Integer> points;

    // --- コンストラクタ ---

    /**
     * デフォルトコンストラクタ。
     * pointsマップを初期化します。
     */
    public TestListSubject() {
        this.points = new HashMap<>();
    }

    // --- メソッド (getter/setter) ---

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    /**
     * 指定されたキー（テスト回数）に対応する点数を文字列として取得します。
     * JSPなどでの表示を容易にするためのヘルパーメソッドです。
     * 点数が存在しない場合は空文字列を返します。
     *
     * @param key テストの回数
     * @return 点数を表す文字列。存在しない場合は空文字列。
     */
    public String getPoint(int key) {
        Integer point = this.points.get(key);
        // 点数が存在すればStringに変換、存在しなければ空文字を返す
        return point != null ? String.valueOf(point) : "";
    }

    /**
     * pointsマップにテスト結果（回数と点数）を追加します。
     * DAOからこのメソッドを呼び出すことで、学生一人に対して複数の成績を蓄積できます。
     *
     * @param key テストの回数
     * @param value 点数
     */
    public void putPoint(int key, int value) {
        this.points.put(key, value);
    }
}