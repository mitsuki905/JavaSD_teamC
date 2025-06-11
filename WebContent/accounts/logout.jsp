<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　ログアウト</h2>
		<p style="background-color: #008b8b;text-align: center">　ログアウトしました</p>

		<br><br>
		<a href="${pageContext.request.contextPath}/accounts/login">ログイン</a>

	</c:param>

</c:import>