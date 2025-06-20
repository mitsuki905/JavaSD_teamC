<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　メニュー</h2>

		<div class="box-container">
			<div class="box_red">
				<li><a href="${pageContext.request.contextPath}/student/student_list">学生管理</a></li>
			</div>

			<div class="box_green">
				成績管理
				<li><a href="${pageContext.request.contextPath}/test/test_regist">成績登録</a></li>
				<li><a href="${pageContext.request.contextPath}/test/test_list">成績参照</a></li>
			</div>

			<div class="box_blue">
				<li><a href="${pageContext.request.contextPath}/subject/subject_list">科目管理</a></li>
			</div>
		</div>

	</c:param>
</c:import>