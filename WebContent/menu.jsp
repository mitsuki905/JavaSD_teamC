<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="mycss/style.css">

<div class="sidebar">
	<nav>
    <ul>
		<li><a href="${pageContext.request.contextPath}/main">メニュー</a></li>
		<li><a href="${pageContext.request.contextPath}/student/student_list">学生管理</a></li>
		<li>成績管理</li>
		<li><a href="${pageContext.request.contextPath}/test/"
				style="margin-left: 1em; text-indent: -1em;">成績登録</a></li>
		<li><a href="${pageContext.request.contextPath}/main/deleteselect"
				style="margin-left: 1em; text-indent: -1em;">成績参照</a></li>
		<li><a href="${pageContext.request.contextPath}/main/deleteselect">科目管理</a></li>
    </ul>
	</nav>
</div>
