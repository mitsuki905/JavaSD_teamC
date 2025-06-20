<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　成績管理(登録)</h2>
		<p class="p1"text-align: center">　登録が完了しました</p>

		<br><br>
		<%-- 成績管理一覧画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/test/test_regist">戻る</a>
		<%-- 成績参照検索画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/test/test_list" class="link-spacing" >成績参照</a>

	</c:param>

</c:import>
