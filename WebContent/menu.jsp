<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header">
	<h1>得点管理システム</h1>
	<span>${ param.teacher }様</span>
    <c:when test="${not empty session_user}">
        <a href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
    </c:when>
</div>

<div class="sidebar">
	<h2 class="menu">メニュー</h2>
	<div>
	    <ul>
			<li><a href="${pageContext.request.contextPath}/main/table">学生一覧</a></li>
			<li><a href="${pageContext.request.contextPath}/main/addinput">学生追加</a></li>
			<li><a href="${pageContext.request.contextPath}/main/updateselect">学生更新</a></li>
			<li><a href="${pageContext.request.contextPath}/main/deleteselect">学生削除</a></li>

	    </ul>
	</div>
</div>
