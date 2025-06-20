<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　科目情報変更</h2>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="${pageContext.request.contextPath}/subject/subject_update_execute" method="post">
			<br>

			<%-- 科目コード --%>
			<div class="mb-3">
				<label for="subjectCd" class="form-label">科目コード</label>
				<input
					type="hidden"
					class="form-control"
					id="subjectCd"
					name="cd"
					value="${cd}"
					readonly
					required>
					<p>${cd }</p>

				<%-- 変更中に別画面から対象の科目が削除された場合のエラー表示 --%>
		        <li class="text-warning error-message-item">${ errorMessage }</li>
			</div>

			<%-- 科目名 --%>
			<div class="mb-3">
				<label for="subjectName" class="form-label">科目名</label>
				<input
					type="text"
					class="form-control"
					id="subjectName"
					name="name"
					value="${name}"
					maxlength="20"
					required>
			</div>

			<%-- ボタンエリア --%>
			<div class="mt-4">
				<%-- 科目情報変更完了画面に遷移 --%>
				<button type="submit" class="btn btn-primary">変更</button>
				<br>
				<br>
				<%-- 科目管理一覧画面に遷移 --%>
				<a href="${pageContext.request.contextPath}/subject/subject_list" class="ms-3">戻る</a>
			</div>

			</form>
		</div>
	</c:param>

</c:import>