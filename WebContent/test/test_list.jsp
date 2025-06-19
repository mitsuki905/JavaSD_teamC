<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">
		<h2>　成績参照</h2>

		<%-- 検索フォーム（科目） --%>
		<div class="bg-light p-3 rounded mb-4">
			<form action="/test/test_list_subject" method="post">
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



					<%-- クラスプルダウン (修正版) --%>
			<div class="col-md-2">
			    <label for="f_class_num" class="form-label">クラス</label>
			    <select name="f3" id="f3" class="form-select">
			        <option value="">--------</option> <%-- 空文字に --%>
			        <%-- "classList"は文字列のリストなので、"classItem"自体がクラス番号になる --%>
			        <c:forEach var="classNum" items="${classList}">
			            <option value="${classNum}" <c:if test="${classNum == fClassNum}">selected</c:if>>
			                ${classNum}
			            </option>
			        </c:forEach>
			    </select>
			</div>

			<%-- 科目プルダウン (修正版) --%>
			<div class="col-md-3">
			    <label for="f_class_num" class="form-label">科目</label>
			    <select name="f2" id="f2" class="form-select">
			        <option value="">--------</option>
			        <%-- "subjectList"はSubjectオブジェクトのリスト --%>
			        <c:forEach var="subject" items="${subjectList}">
			            <%-- valueには科目コード、表示には科目名を使う --%>
			            <option value="${subject.cd}" <c:if test="${subject.cd == fSubjectCd}">selected</c:if>>
			                ${subject.name}
			            </option>
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



		<%-- 検索フォーム（学生） --%>
		<div class="bg-light p-3 rounded mb-4">
			<form action="/test/test_list_student" method="post">
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
								<input type="hidden" name="f" value="st">
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
		科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
		 </font></label>
		 <%-- studentsがnullでない場合（＝検索が実行された後）にのみ、このブロック全体を表示する --%>
		<c:if test="${students != null}">
			<div class="mt-4">
				<%-- 検索された科目名を表示 --%>
				<c:if test="${not empty subjectName}">
					<h3 class="h5 mb-3">科目：<c:out value="${subjectName}" /></h3>
				</c:if>

				<%-- 検索結果テーブル --%>
				<div class="table-responsive">
					<table class="table table-striped table-hover">
						<%-- テーブルヘッダー --%>
						<thead>
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>1回</th>
								<th>2回</th>
							</tr>
						</thead>
						<%-- テーブルデータ --%>
						<tbody>
							<%-- リストが空でない場合にのみループ処理 --%>
							<c:forEach var="student" items="${students}">
								<tr>
									<td>${student.entYear}</td>
									<td>${student.classNum}</td>
									<td>${student.no}</td>
									<td>${student.name}</td>
									<%-- 1回目の点数。ない場合はハイフンを表示 --%>
									<td>
										<c:choose>
											<c:when test="${student.point1 != null && student.point1 >= 0}">
												${student.point1}
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
									<%-- 2回目の点数。ない場合はハイフンを表示 --%>
									<td>
										<c:choose>
											<c:when test="${student.point2 != null && student.point2 >= 0}">
												${student.point2}
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


			</div>
		</c:if>


	</c:param>
</c:import>