<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">

<c:import url="/base1.jsp">
	<c:param name="body">


            <%-- ヘッダー：学生情報登録 --%>
		<h2 style="background-color: #f0f0f0;">学生情報登録</h2>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="【フォームの送信先URL】" method="post">

				<%-- 入学年度 --%>
				<div class="mb-3">
					<label for="ent_year" class="form-label">入学年度</label>
					<%--
                            Java側で request.setAttribute("yearList", ...) のように渡された
                            年リスト（yearList）をループして<option>を生成します。
                        --%>
					<select class="form-select" id="ent_year" name="ent_year">
						<c:forEach var="year" items="${yearList}">
							<%-- 編集画面などで初期値を選択状態にするためのif文 --%>
							<option value="${year}"
								<c:if test="${year == entYear}">selected</c:if>>${year}</option>
						</c:forEach>
					</select>
				</div>

				<%-- 学生番号 --%>
				<div class="mb-3">
					<label for="studentId" class="form-label">学生番号</label> <input
						type="text" class="form-control" id="studentId" name="studentId"
						placeholder="学生番号を入力してください">
				</div>

				<%-- 氏名 --%>
				<div class="mb-3">
					<label for="student_name" class="form-label">氏名</label> <input
						type="text" class="form-control" id="student_name"
						name="class_num" placeholder="氏名を入力してください">
				</div>

				<%-- クラス --%>

				<div class="mb-3">
					<label for="class" class="form-label">クラス</label> <select
						class="form-select" id="class_num" name="class_num">
						<c:forEach var="classItem" items="${classList}">
							<option value="${classItem.classNum}"
								<c:if test="${classItem.classNum == classNum}">selected</c:if>>
								${classItem.classNum}</option>
						</c:forEach>
					</select>
				</div>

				<%-- ボタンエリア --%>
				<div class="mt-4">
					<button type="submit" class="btn btn-secondary">登録して終了</button>
					<br>
					<br> <a
						href="${pageContext.request.contextPath}/student/student_list"
						class="ms-3">戻る</a>
				</div>

			</form>
		</div>


	</c:param>
</c:import>