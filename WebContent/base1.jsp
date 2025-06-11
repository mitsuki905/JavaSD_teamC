<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
	<head>
	<title>得点管理システム</title>
	<link rel="stylesheet" href="css/style.css">
	</head>

	<body>
		<div class="header">
			<h1>得点管理システム</h1>

			<%-- ログインができたときの処理 --%>
			<%-- <c:if test="${not empty session_user}"> --%>
				<div class="user-info">
					<%-- <span>${session_user} 様</span> --%>
					<span>大原　太郎様</span>
					<a href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
				</div>
			<%-- </c:if> --%>
		</div>

			<%-- ログインページでは共通メニュー画面が表示されない --%>
			<%-- <c:if test="${not empty session_user}"> --%>
				<c:import url="/menu.jsp" />
			<%-- </c:if> --%>

		<div class="contents">
			${ param.body}
		</div>

		<%-- 見た目を優先してsmallタグに変更 --%>
		<div class="footer">
			<small>© 2025 TIC</small><br>
			<small>大原学園</small>
		</div>
	</body>
</html>