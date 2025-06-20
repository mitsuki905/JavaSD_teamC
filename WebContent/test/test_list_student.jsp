<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<c:url value='/css/style.css'/>
<c:import url="/base.jsp">
    <c:param name="body">
        <h2>　成績一覧（学生）</h2>

        <form method="post">
		    <%-- 検索フォーム（科目） --%>
		    <div class="bg-light p-3 rounded mb-4">
		        <div class="row g-3 align-items-end">科目情報　　　　　
		            <div class="col-md-2">
		                <label for="f1" class="form-label">入学年度</label>
		                <select name="f1" id="f1" class="form-select">
		                    <option value="0">--------</option>
		                    <c:forEach var="year" items="${yearList}">
		                        <option value="${year}" <c:if test="${year == fEntYear}">selected</c:if>>${year}</option>
		                    </c:forEach>
		                </select>
		            </div>

		            <div class="col-md-2">
		                <label for="f3" class="form-label">クラス</label>
		                <select name="f3" id="f3" class="form-select">
		                    <option value="0">--------</option>
		                    <c:forEach var="classNum" items="${classList}">
		                        <option value="${classNum}" <c:if test="${classNum == fClassNum}">selected</c:if>>${classNum}</option>
		                    </c:forEach>
		                </select>
		            </div>

		            <div class="col-md-3">
		                <label for="f2" class="form-label">科目</label>
		                <select name="f2" id="f2" class="form-select">
		                    <option value="0">--------</option>
		                    <c:forEach var="subject" items="${subjectList}">
		                        <option value="${subject.cd}" <c:if test="${subject.cd == fSubjectCd}">selected</c:if>>${subject.name}</option>
		                    </c:forEach>
		                </select>
		            </div>

		            <div class="col-md-2 d-flex justify-content-end">
		                <button type="submit" class="btn btn-secondary" formaction="${pageContext.request.contextPath}/test/test_list_subject">検索</button>
		            </div>
		        </div>
		        <%-- 科目検索のエラーメッセージ表示 --%>
		        <c:if test="${not empty error_subject}">
		            <p class="text-warning mt-2">${error_subject}</p>
		        </c:if>
		    </div>

		    <%-- 検索フォーム（学生） --%>
		    <div class="bg-light p-3 rounded mb-4">
		        <div class="row g-3 align-items-end">学生情報　　　　　
		            <div class="col-md-3">
		                <label for="f4" class="form-label">学生番号</label>
		                <input type="text" class="form-control" id="f4" name="f4" value="${no}" placeholder="学生番号を入力してください" maxlength="10">
		            </div>

		            <div class="col-md-2 d-flex justify-content-end">
		                <button type="submit" class="btn btn-secondary" formaction="${pageContext.request.contextPath}/test/test_list_student">検索</button>
		            </div>
		        </div>
		        <%-- 学生検索のエラーメッセージ表示 --%>
		         <c:if test="${not empty error_student}">
		            <p class="text-warning mt-2">${error_student}</p>
		        </c:if>
		    </div>
		</form>


        <%-- 検索された学生の情報を表示 --%>
        <div class="mb-3">
            <c:choose>
                <%-- 学生が見つかった場合 --%>
                <c:when test="${not empty student}">
                    <p>氏名：${student.name} (${student.no})</p>
                </c:when>
                <%-- 学生が見つからなかった場合 --%>
                <c:otherwise>
                    <p>氏名：該当者なし (${no})</p>
                </c:otherwise>
            </c:choose>
        </div>

        <%-- 検索結果が0件、または学生が見つからなかった場合のメッセージ --%>
        <c:if test="${empty tests}">
            <p>成績情報が存在しませんでした</p>
        </c:if>

        <%-- 検索結果がある場合のみテーブルを表示 --%>
        <c:if test="${not empty tests}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>科目名</th>
                            <th>科目コード</th>
                            <th>回数</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- 変数名を "tests" に、ループ変数を "test" に修正 --%>
                        <c:forEach var="test" items="${tests}">
                            <tr>
                                <%-- TestListStudentのプロパティに合わせて表示 --%>
                                <td>${test.subjectName}</td>
                                <td>${test.subjectCd}</td>
                                <td>${test.num}</td>
                                <td>${test.point}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </c:param>
</c:import>