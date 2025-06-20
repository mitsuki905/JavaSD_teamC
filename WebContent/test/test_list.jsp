<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">
    <c:param name="body">
        <h2>　成績参照</h2>

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
		                <%-- required設定 --%>
		                <%-- <input type="text" class="form-control" id="f4" name="f4" value="${no}" placeholder="学生番号を入力してください" maxlength="10" required> --%>
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

        <label><font color="#1e90ff">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</font></label>

        <%-- サーブレット全体で発生したエラーメッセージの表示 --%>
        <c:if test="${not empty error_message}">
            <p class="text-warning mt-2">${error_message}</p>
        </c:if>
    </c:param>
</c:import>