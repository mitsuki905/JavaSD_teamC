package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 成績一覧（科目別）の学生一人分のデータを格納するBeanクラス。
 */
public class TestListSubject implements Serializable {

    // --- フィールド ---
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private Map<Integer, Integer> points;

    // --- コンストラクタ ---
    public TestListSubject() {
        this.points = new HashMap<>();
    }

    // --- 既存のメソッド (getter/setter) ---

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

    public void putPoint(int key, int value) {
        this.points.put(key, value);
    }

    // このメソッドは現在直接使われていませんが、残しておいても問題ありません。
    public String getPoint(int key) {
        Integer point = this.points.get(key);
        return point != null ? String.valueOf(point) : "";
    }


    // --- ここからがエラー解決のための追加メソッド ---

    /**
     * JSPで ${student.point1} によって1回目のテストの点数を取得するためのメソッドです。
     * EL式は自動的にこの `getPoint1()` メソッドを呼び出します。
     * @return 1回目の点数。存在しない場合はnullを返します。
     */
    public Integer getPoint1() {
        return this.points.get(1);
    }

    /**
     * JSPで ${student.point2} によって2回目のテストの点数を取得するためのメソッドです。
     * EL式は自動的にこの `getPoint2()` メソッドを呼び出します。
     * @return 2回目の点数。存在しない場合はnullを返します。
     */
    public Integer getPoint2() {
        return this.points.get(2);
    }
}