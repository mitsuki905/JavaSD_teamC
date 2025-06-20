<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　科目情報登録</h2>
		<p class="p1">　登録が完了しました</p>

		<br><br>
		<%-- 科目登録画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/subject/subject_create">戻る</a>
		<%-- 科目管理一覧画面に遷移 --%>
		<a href="${pageContext.request.contextPath}/subject/subject_list" class="link-spacing" >科目一覧</a>
	</c:param>

</c:import>