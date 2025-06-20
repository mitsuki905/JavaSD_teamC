<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<div class="card-header">
			<h2>学生情報登録</h2>
		</div>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="student_create_done" method="post">
				<%-- 入学年度 --%>
				<div class="mb-3">
					<br><label for="ent_year" class="form-label">
						入学年度
					</label>
					<%-- プルダウン式 --%>
					<select class="form-select" id="ent_year" name="ent_year">
					<option value="0">
									--------
								</option>
						<c:forEach var="year" items="${entYear}">
							<option value="${year}"
								<c:if test="${year == ent_year}">selected</c:if>>${year}
							</option>
						</c:forEach>
					</select>

					<!-- エラー表示 -->
					<c:if test="${not empty error_ent_year}">
						<div class="text-warning mt-1">${error_ent_year}</div>
						<p>入学年度を選択してください</p>
					</c:if>
				</div>



				<%-- 学生番号 --%>
				<div class="mb-3">
					<%-- labelのfor属性をinputのidと一致させる --%>
					<label for="no" class="form-label">
						学生番号
					</label>
					<input type="text"
							class="form-control"
							id="no"
							name="no"
							value="${no}"
							placeholder="学生番号を入力してください"
							maxlength="10" <%-- 最大10文字に制限 --%>
							required> <%-- 必須入力 --%>

					<c:if test="${not empty error_no}">
						<div class="text-warning mt-1">
							${error_no}
						</div>

					</c:if>
				</div>



				<!-- エラーを出したときに入力されたを保持する -->
				<%-- 氏名 --%>
				<div class="mb-3">
					<label for="student_name" class="form-label">
						氏名
					</label>
					<input type="text"
							class="form-control"
							id="name"
							name="name"
							value="${name}"
							placeholder="氏名を入力してください"
							maxlength="30"
					required>
				</div>



				<%-- クラス --%>
				<div class="mb-3">
					<label for="class_num" class="form-label">
						クラス
					</label>
					<select class="form-select"
							id="class_num"
							name="class_num">
						<c:forEach var="class_num" items="${classList}">
							<option value="${class_num.class_num}"
								<c:if test="${class_num.class_num == classNum}">selected
								</c:if>>${class_num.class_num}
							</option>
						</c:forEach>
					</select>
				</div>



				<%-- ボタンエリア --%>
				<div class="mt-4">
					<button type="submit" class="btn btn-secondary" name="end">登録して終了</button>
					<%-- 学生管理一覧画面に遷移 --%>
					<a href="${pageContext.request.contextPath}/main/main" class="d-block mt-2">戻る</a>
				</div>
			</form>
		</div>

	</c:param>
</c:import>