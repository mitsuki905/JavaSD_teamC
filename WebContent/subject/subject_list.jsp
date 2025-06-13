<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　科目管理</h2>

		<div class="text-end mb-2">
			<a href="${pageContext.request.contextPath}/subject/subject_update">新規登録</a>
		</div>

		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<%-- テーブルの型 --%>
				<thead>
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<%-- Java側から渡された subject リストをループ処理 --%>
					<c:forEach var="subject" items="${subject}">
						<tr>
							<td>${subject.cd}</td>
							<input type="hidden" name="cd" value="${subject.cd}">
							<td>${subject.name}</td>
							<input type="hidden" name="name" value="${subject.name}">
							<td><a href="${pageContext.request.contextPath}/subject/subject_update">変更</a></td>
							<td><a href="${pageContext.request.contextPath}/subject/subject_delete">削除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</c:param>
</c:import>