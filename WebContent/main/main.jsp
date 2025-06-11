<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
 --><c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　メニュー</h2>

		<div class="box-container">
			<div class="box_red">
			<li><a href="${pageContext.request.contextPath}/student/student_list">学生管理</a></li>
			</div>

			<div class="box_green">成績管理
			<li><a href="${pageContext.request.contextPath}/test/">成績登録</a></li>
			<li><a href="${pageContext.request.contextPath}/main/deleteselect">成績参照</a></li>
			</div>

			<div class="box_blue">
			<li><a href="${pageContext.request.contextPath}/main/deleteselect">科目管理</a></li>
			</div>
		</div>

	</c:param>

</c:import>