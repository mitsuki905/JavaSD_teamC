<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="css/style.css">
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<div class="sidebar">
	<nav>
    <ul>
		<li><a href="${pageContext.request.contextPath}/main/main">メニュー</a></li>
		<li><a href="${pageContext.request.contextPath}/student/student_list">学生管理</a></li>
		<li>成績管理</li>
		<li><a href="${pageContext.request.contextPath}/test/"
				style="margin-left: 1em; text-indent: -1em;">成績登録</a></li>
		<li><a href="${pageContext.request.contextPath}/main/deleteselect"
				style="margin-left: 1em; text-indent: -1em;">成績参照</a></li>
		<li><a href="${pageContext.request.contextPath}/subject/subject_list">科目管理</a></li>
    </ul>
	</nav>
</div>
