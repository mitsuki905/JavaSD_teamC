<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base1.jsp">

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
					        <td>${subject.name}</td>
					        <%-- 変更リンクのURLにパラメータとして科目コード(cd)を追加 --%>
					        <td><a href="${pageContext.request.contextPath}/subject/subject_update?cd=${subject.cd}">変更</a></td>
					        <%-- 削除リンクも同様 --%>
					        <td><a href="${pageContext.request.contextPath}/subject/subject_delete?cd=${subject.cd}">削除</a></td>
					    </tr>
					</c:forEach>

				</tbody>
			</table>
		</div>

	</c:param>
</c:import>