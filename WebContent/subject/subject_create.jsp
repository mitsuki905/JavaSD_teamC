<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">

<c:import url="/base1.jsp">
	<c:param name="body">


            <%-- ヘッダー：学生情報登録 --%>
		<h2 style="background-color: #f0f0f0;">科目情報登録</h2>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="${pageContext.request.contextPath}/subject/subject_create_done" method="post">
			<br>

				<%-- 科目コード --%>
				<div class="mb-3">
					<label for="studentId" class="form-label">科目コード</label> <input
						type="text" class="form-control" id="studentId" name="studentId"
						placeholder="科目コードを入力してください" required>
				</div>

				<%-- 科目名 --%>
				<div class="mb-3">
					<label for="student_name" class="form-label">科目名</label> <input
						type="text" class="form-control" id="student_name"
						name="class_num" placeholder="科目名を入力してください" required>
				</div>

				<%-- ボタンエリア --%>
				<div class="mt-4">
					<button type="submit" class="btn btn-primary">登録</button>
					<br>
					<br> <a href="${pageContext.request.contextPath}/student/student_list" class="ms-3">戻る</a>
				</div>

			</form>
		</div>

	</c:param>
</c:import>