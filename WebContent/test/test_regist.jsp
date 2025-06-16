<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">
	<c:param name="body">
		<h2 style="background-color: #f0f0f0;">　成績参照</h2>



		<div class="bg-light p-3 rounded mb-4">
			<form action="/student/student_list" method="post">
				<div class="row g-3 align-items-end">科目情報　　　　　
					<div class="col-md-2">
						<label  for="f_ent_year"
								class="form-label">入学年度
						</label>
						<select name="f1"
								id="f1"
								class="form-select">
								<option value="0">
									--------
								</option>
								<c:forEach var="year" items="${yearList}">
									<option value="${year}"
										<c:if test="${year == fEntYear}">selected</c:if>>${year}
									</option>
								</c:forEach>
						</select>
					</div>



					<div class="col-md-2">
						<label  for="f_class_num"
							    class="form-label">クラス
						</label>
						<select name="f3"
								id="f3"
								class="form-select">
								<option value="0">
									--------
								</option>
								<c:forEach var="classItem" items="${classList}">
									<option value="${classItem.classNum}"
										<c:if test="${classItem.classNum == fClassNum}">selected
										</c:if>>${classItem.classNum}
									</option>
								</c:forEach>
						</select>
					</div>



					<div class="col-md-3">
						<label  for="f_class_num"
							    class="form-label">科目
						</label>
						<select name="f2"
								id="f2"
								class="form-select">
								<option value="0">
									--------
								</option>
								<c:forEach var="classItem" items="${classList}">
									<option value="${classItem.classNum}"
										<c:if test="${classItem.classNum == fClassNum}">selected
										</c:if>>${classItem.classNum}
									</option>
									<input type="hidden" name="f" value="st">
								</c:forEach>
						</select>
					</div>



					<div class="col-md-2 d-flex justify-content-end">
						<button type="submit"
								class="btn btn-secondary">検索
						</button>
					</div>
				</div>
			</form>
		</div>



		<div class="bg-light p-3 rounded mb-4">
			<form action="/student/student_list" method="post">
				<div class="row g-3 align-items-end">学生情報　　　　　
					<div class="col-md-3">
							<label  for="f_class_num"
								    class="form-label">学生番号
							</label>
						<input type="text"
								class="form-control"
								id="f4"
								name="f4"
								value="${no}"
								placeholder="学生番号を入力してください"
								maxlength="10" <%-- 最大10文字に制限 --%>
								required> <%-- 必須入力 --%>
								<input type="hidden" name="f" value="sj">
					</div>



					<div class="col-md-2 d-flex justify-content-end">
						<button type="submit"
								class="btn btn-secondary">検索
						</button>
					</div>
				</div>
			</form>
		</div>

		<label><font color="#1e90ff">
		<!-- <input type="hidden" name="f" value="sj">
		<input type="hidden" name="f" value="st"> -->
		科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
		 </font>
		</label>

	</c:param>
</c:import>