<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　成績管理</h2>
		<p style="background-color: #008b8b;text-align: center">　登録が完了しました</p>

		<br><br>
		<a href="${pageContext.request.contextPath}/test/test_create" class="link-spacing" >戻る</a>
		<a href="${pageContext.request.contextPath}/test/test_list" class="link-spacing" >成績参照</a>

	</c:param>

</c:import>