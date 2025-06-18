<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　ログアウト</h2>
		<p class="p1">　ログアウトしました</p>

		<br><br>
		<a href="${pageContext.request.contextPath}/accounts/login">ログイン</a>

	</c:param>
</c:import>