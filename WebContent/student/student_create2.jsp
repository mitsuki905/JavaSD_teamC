<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">

<c:import url="/base1.jsp">
	<c:param name="body">


		<div class="card-header">
			<%-- ヘッダー：学生情報登録 --%>
			<h2 class="h4 mb-0">学生情報登録</h2>
		</div>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="/student/student_create_done" method="post">

				<%-- 入学年度 --%>
				<div class="mb-3">
					<label for="ent_year" class="form-label">入学年度</label> <select
						class="form-select" id="ent_year" name="ent_year">

						<c:forEach var="year" items="${yearList}">
							<option value="${year}"
								<c:if test="${year == entYear}">selected</c:if>>${year}</option>
						</c:forEach>
					</select>
					<%-- ▼▼【変更点】入学年度のエラーメッセージ表示エリア --%>

					<c:if test="${not empty error_ent_year}">
						<div class="text-warning mt-1">${error_ent_year}</div>
					</c:if>
				</div>

				<%-- 学生番号 --%>
				<div class="mb-3">
					<label for="studentId" class="form-label">学生番号</label>
					<%-- ▼▼【変更点】エラー時に値を保持するため value を追加 --%>
					<input type="text" class="form-control" id="studentId"
						name="studentId" value="${studentId}" placeholder="学生番号を入力してください"
						required>
					<%-- ▼▼▼ この行を変更 ▼▼▼ --%>
					<c:if test="${not empty error_student_id}">
						<div class="text-warning mt-1">${error_student_id}</div>
					</c:if>
				</div>

				<%-- 氏名 --%>
				<div class="mb-3">
					<label for="student_name" class="form-label">氏名</label>
					<%-- ▼▼【変更点】name属性を修正、エラー時に値を保持するため value を追加 --%>
					<input type="text" class="form-control" id="student_name"
						name="student_name" value="${student_name}"
						placeholder="氏名を入力してください" required>
				</div>

				<%-- クラス --%>
				<div class="mb-3">
					<label for="class_num" class="form-label">クラス</label> <select
						class="form-select" id="class_num" name="class_num">

						<c:forEach var="classItem" items="${classList}">
							<option value="${classItem.classNum}"
								<c:if test="${classItem.classNum == classNum}">selected</c:if>>${classItem.classNum}</option>
						</c:forEach>
					</select>
					<%-- クラスの未選択エラーも表示する場合はここに同様に追加 --%>
				</div>

				<%-- ボタンエリア --%>
				<div class="mt-4">
					<button type="submit" class="btn btn-secondary">登録して終了</button>
					<a href="${pageContext.request.contextPath}/main"
						class="d-block mt-2">戻る</a>
				</div>
			</form>
		</div>
		</div>
	</c:param>
</c:import>