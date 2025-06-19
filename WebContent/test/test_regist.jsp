<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">
    <c:param name="body">

        <h2>　成績管理</h2><br>

        <%-- エラーメッセージ表示エリア --%>
        <c:if test="${not empty error}">
            <div class="alert alert-warning">${error}</div>
        </c:if>

        <%-- 検索フォーム --%>
        <div class="bg-light p-3 rounded mb-4">
            <form action="test_regist" method="post">
                <div class="row g-3 align-items-end">

                    <%-- 入学年度 --%>
                    <div class="col-md-2">
                        <label for="f_ent_year" class="form-label">入学年度</label>
                        <select name="f_ent_year" id="f_ent_year" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${entYearSet}">
								<option value="${year}" <c:if test="${year == f_ent_year}">selected</c:if>>${year}</option>
							</c:forEach>
                        </select>
                    </div>

                    <%-- クラス --%>
                    <div class="col-md-2">
                        <label for="f_class_num" class="form-label">クラス</label>
                        <select name="f_class_num" id="f_class_num" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="classItem" items="${classList}">
                                <option value="${classItem}" <c:if test="${classItem == f_class_num}">selected</c:if>>${classItem}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 科目 --%>
                    <div class="col-md-3">
                        <label for="f_subject_cd" class="form-label">科目</label>
                        <select name="f_subject_cd" id="f_subject_cd" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="subjectItem" items="${subjectList}">
                                <option value="${subjectItem.cd}" <c:if test="${subjectItem.cd == f_subject_cd}">selected</c:if>>${subjectItem.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 回数 --%>
                    <div class="col-md-2">
                        <label for="f_num" class="form-label">回数</label>
                        <select name="f_num" id="f_num" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="i" begin="1" end="5">
                                <option value="${i}" <c:if test="${i == f_num}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-2 d-flex justify-content-end">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </div>
            </form>
        </div>

        <%-- 検索後、該当者がいなかった場合にメッセージ表示 --%>
        <c:if test="${not empty f_ent_year && f_ent_year != 0 && empty students}">
            <p>該当する学生は見つかりませんでした</p>
        </c:if>

        <%-- 検索結果の表示 --%>
        <c:if test="${not empty students}">
            <form action="test_regist_execute" method="post">
                <p>科目：${subject.name} (${num}回)</p>
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${students}">
                                <tr>
                                    <td>${student.entYear}</td>
                                    <td>${student.classNum}</td>
                                    <td>${student.no}</td>
                                    <td>${student.name}</td>
                                    <td>
                                        <input type="text" name="point_${student.no}" class="form-control" value="${points[student.no]}">
                                        <%-- 不要なエラー表示(liタグ)を削除しました --%>
                                        <c:if test="${not empty errors[student.no]}">
                                            <%-- エラーメッセージの色を text-danger (赤色) に変更しました --%>
                                            <div class="text-danger small">${errors[student.no]}</div>
                                        </c:if>
                                    </td>
                                    <input type="hidden" name="student_no" value="${student.no}">
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <%-- 登録処理に必要な共通情報をhiddenで送信 --%>
                    <input type="hidden" name="subject_cd" value="${subject.cd}">
                    <input type="hidden" name="num" value="${num}">
                    <input type="hidden" name="ent_year" value="${f_ent_year}">
                    <input type="hidden" name="class_num" value="${f_class_num}">

                    <div class="mt-4">
                        <%-- ボタンのクラス名を btn-primary に戻しました --%>
                        <button type="submit" class="btn btn-primary">登録して終了</button>
                    </div>
                </div>
            </form>
        </c:if>

    </c:param>
</c:import>