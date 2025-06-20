<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　学生情報登録</h2>
		<p class="p1">　登録が完了しました</p>

		<br><br>
		<%-- 学生登録画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/student/student_create">戻る</a>
		<%-- 学生管理一覧画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/student/student_list" class="link-spacing" >学生一覧</a>
	</c:param>

</c:import>