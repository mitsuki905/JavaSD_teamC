<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　学生情報登録</h2>
		<p style="background-color: #008b8b;text-align: center">　登録が完了しました</p>

		<br><br>
		<a href="${pageContext.request.contextPath}/student/student_create">戻る</a>
		<a href="${pageContext.request.contextPath}/studebt/student_list" class="link-spacing" >学生一覧</a>
	</c:param>

</c:import>