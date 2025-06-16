<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base1.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　科目情報変更</h2>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="${pageContext.request.contextPath}/subject/subject_update_execute" method="post">
			<br>

			<%-- 科目コード --%>
			<div class="mb-3">
				<label for="subjectCd" class="form-label">科目コード</label>]
				<input
					type="text"
					class="form-control"
					id="subjectCd"
					name="cd"
					value="${cd}"
					required>
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
				<button type="submit" class="btn btn-primary">変更</button>
				<br>
				<br>
				<a href="${pageContext.request.contextPath}/subject/subject_list" class="ms-3">戻る</a>
			</div>

			</form>
		</div>
	</c:param>

</c:import>