<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">学生管理</h2>


		<div class="text-end mb-2">
			<a href="${pageContext.request.contextPath}/student/student_create">新規登録</a>
		</div>


		<div class="bg-light p-3 rounded mb-4">
			<form action="${pageContext.request.contextPath}/student/student_list" method="post">
				<div class="row g-3 align-items-end">

					<div class="col-md-3">
						<label for="f_ent_year" class="form-label">入学年度</label> <select
							name="year" id="f_ent_year" class="form-select">
							<option value="0">
									--------
								</option>
							<c:forEach var="year" items="${yearList}">
								<option value="${year}"
									<c:if test="${year == fEntYear}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-3">
						<label  for="f_class_num"
							    class="form-label">クラス
						</label>
						<select name="classItem"
								id="f2"
								class="form-select">
								<option value="0">
									--------
								</option>
							<c:forEach var="classItem" items="${classList}">
								<option value="${classItem}"
									<c:if test="${classItem == fClassNum}">selected</c:if>>${classItem}</option>
							</c:forEach>
						</select>
					</div>



					<div class="col-md-2">
						<div class="form-check">
							<input  class="form-check-input"
									type="checkbox"
									name="isAttend"
									id="f3"
									value="true"
							<c:if test="${isAttend}">
								checked
							</c:if>>
							<label class="form-check-label" for="is_attend">
								在学中
							</label>
						</div>
					</div>



					<!-- 指定されている条件に該当する学生情報を取得して一覧に表示する -->
					<div class="col-md-2 d-flex justify-content-end">
						<button type="submit"
								class="btn btn-secondary">絞り込み
						</button>
					</div>
				</div>
			</form>
		</div>


		<p>
			検索結果：
			<c:out value="${students.size()}" />
			<!-- リクエストに表示されている学生リストの件数を表示する -->
			件
		</p>


		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>入学年度</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>クラス</th>
						<th>在学中</th>
						<th></th>
						<%-- 変更リンク用の空ヘッダー --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="student" items="${students}">
					    <tr>
					    	<!--学生情報管理テーブルの「入学年度」カラムの値を表示する -->
					        <td>${student.entYear}</td>
					        <!-- 学生情報管理テーブルの「学生番号」カラムの値を表示する -->
					        <td>${student.no}</td>
					        <!-- 学生情報管理テーブルの「学生氏名」カラムの値を表示する -->
					        <td>${student.name}</td>
					        <!-- 学生情報管理テーブルの「クラス番号」カラムの値を表示する -->
					        <td>${student.classNum}</td>
					        <td>
					        	<!-- 学生情報管理テーブルの「在学中フラグ」カラムの値を変換して表示する -->
					            <c:if test="${student.isAttend}">○</c:if>
					            <c:if test="${!student.isAttend}">×</c:if>
					        </td>
					        <%-- 変更リンクのURLに、キーとなるentyear,no,name,numをパラメータとして追加 --%>
					        <td>
					            <a href="${pageContext.request.contextPath}/student/student_update?
					            	entyear=${student.entYear}&no=${student.no}&name=${student.name}&classNum=${student.classNum}">変更</a>
					        </td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>




	</c:param>
</c:import>