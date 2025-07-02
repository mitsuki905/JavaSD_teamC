<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
	<title>得点管理システム</title>
	<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>

<body style="margin-left:10%;margin-right:10%;">
	<div class="header">
		<h1>得点管理システム</h1>
		<c:if test="${not empty teacher}">
			<div class="user-info">
				<span>${teacher.name} 様</span>
				<a href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
			</div>
		</c:if>
	</div>

	<%-- ログイン時だけサイドバーを表示 --%>
	<c:if test="${not empty teacher}">
		<c:import url="/menu.jsp" />
	</c:if>

	<%-- teacherが空(＝ログインしていない)時 body部分を画面いっぱいにする --%>
	<div class="contents <c:if test='${empty teacher}'>full-width</c:if>">
		${ param.body}
	</div>

	<div class="footer">
		<small>© 2025 TIC</small><br>
		<small>大原学園</small>
	</div>
</body>
</html>
