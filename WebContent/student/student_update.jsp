<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">
	<c:param name="body">
		<div class="card-header">
			<%-- ヘッダー：学生情報登録 --%>
			<h2 style="background-color: #f0f0f0;">学生情報変更</h2>
		</div>



		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="/student/student_update_done" method="post">
				<%-- 変更不可 --%>
				<div class="mb-3">
					<br><label class="form-label">
							入学年度
						</label>
					<p class="form-control-plaintext" readonly>${student.ent_year}</p>
				</div>



				<%-- 学生番号 変更不可 --%>
				<div class="mb-3">
					<label class="form-label">
						学生番号
					</label>
					<p class="form-control-plaintext">
						${student.no}
					</p>
					<%-- どの学生を更新するかをサーバーに伝えるためにhiddenフィールドで送信する --%>
					<input type="hidden" name="no" value="${student.no}" readonly>
				</div>



				<%--  氏名 変更可能 --%>
				<div class="mb-3">
					<label for="student_name" class="form-label">
						氏名
					</label>
					<input
						type="text"
						class="form-control"
						id="name"
						name="name"
						value="${student.name}"
						maxlength="30"
					required>
				</div>



				<%--  クラス 変更可能 --%>
				<div class="mb-3">
					<label for="class_num" class="form-label">
					クラス
					</label>
					<select class="form-select" id="class_num" name="class_num">
						<c:forEach var="classItem" items="${classList}">
							<option value="${classItem.num}"
								<c:if test="${classItem.num == student.num}">
									selected
								</c:if>>
								${classItem.num}
							</option>
						</c:forEach>
					</select>
				</div>



				<%-- 在学中チェックボックス (文字の右側に配置) --%>
				<div class="d-flex align-items-center mb-4">
					<label for="is_attend" class="me-2">
						在学中
					</label>
					<input
						class="form-check-input"
						type="checkbox"
						id="is_attend"
						name="is_attend"
						value="true"
						<c:if test="${student.isattend}">
							checked
						</c:if>>
				</div>



				<%-- ボタンエリア --%>
				<div class="mt-4">
					<%-- 変更ボタン --%>
					<button type="submit" class="btn btn-primary">
						変更
					</button>
					<%-- 戻るリンク --%>
					<a href="${pageContext.request.contextPath}/student/student_list"
						class="d-block mt-2">戻る</a>
				</div>
			</form>
		</div>
	</c:param>
</c:import>