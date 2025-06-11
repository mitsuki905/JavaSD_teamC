<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/main.css">
<title>得点管理システム</title>

</head>

<body>
	<div class="header">
		<h1>得点管理システム</h1>
		<c:if test="${not empty session_user}">
			<div class="user-info">
				<span>${session_user} 様</span> <a
					href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
			</div>
		</c:if>
	</div>

	<c:import url="/menu.jsp" />
	${ param.body }

	<div class="footer">
		<p>© 2025 TIC</p>
		<p>大原学園</p>
	</div>
</body>
</html>