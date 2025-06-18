<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　科目情報変更</h2>
		<p class="p1">　変更が完了しました</p>

		<br><br>
		<%-- 科目管理一覧画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/subject/subject_list">科目一覧</a>
	</c:param>

</c:import>