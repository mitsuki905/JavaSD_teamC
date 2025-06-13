<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base1.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">成績管理</h2><br>

		<%-- 検索フォーム --%>
		<div class="bg-light p-3 rounded mb-4">
			<form action="test/test_list" method="post">
				<div class="row g-3 align-items-end">

					<%-- ${  }内の変数名の変更お願い --%>
					<div class="col-md-2">
						<label for="f_ent_year" class="form-label">入学年度</label> <select
							name="f1" id="f1" class="form-select">
							<option value="0">--------</option>
							<c:forEach var="year" items="${yearList}">
								<option value="${year}"
									<c:if test="${year == fEntYear}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>

					<%-- ${  }内の変数名の変更お願い --%>
					<div class="col-md-2">
						<label for="f_class_num" class="form-label">クラス</label> <select
							name="f2" id="f2" class="form-select">
							<option value="0">--------</option>
							<c:forEach var="classItem" items="${classList}">
								<option value="${classItem.classNum}"
									<c:if test="${classItem.classNum == fClassNum}">selected</c:if>>${classItem.classNum}</option>
							</c:forEach>
						</select>
					</div>

					<%-- ${  }内の変数名の変更お願い --%>
					<div class="col-md-3">
						<label for="f_class_num" class="form-label">科目</label> <select
							name="f3" id="f3" class="form-select">
							<option value="0">--------</option>
							<c:forEach var="classItem" items="${classList}">
								<option value="${classItem.classNum}"
									<c:if test="${classItem.classNum == fClassNum}">selected</c:if>>${classItem.classNum}</option>
							</c:forEach>
						</select>
					</div>

					<%-- ${  }内の変数名の変更お願い --%>
					<div class="col-md-2">
						<label for="f_class_num" class="form-label">回数</label> <select
							name="f4" id="f4" class="form-select">
							<option value="0">--------</option>
							<c:forEach var="classItem" items="${classList}">
								<option value="${classItem.classNum}"
									<c:if test="${classItem.classNum == fClassNum}">selected</c:if>>${classItem.classNum}</option>
							</c:forEach>
						</select>
					</div>



					<div class="col-md-2 d-flex justify-content-end">
						<button type="submit" class="btn btn-secondary">検索</button>
					</div>
				</div>
			</form>
		</div>


	<%-- 検索ボタンをクリック後、検索結果を表示 --%>
	<c:forEach var="product" items="${ products }">

		<%-- 該当学生がいないときに表示する(設計書にない画面) --%>
	    <c:if test="${ empty products }">
	      <p>該当する学生は見つかりませんでした</p>
	    </c:if>

		<form action="test/test_" method="post">
			<div class="table-responsive">
				<table class="table table-striped table-hover">
					<p>科目：${ subject.name }(${ subject.num }回)</p>
					<thead>
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${student.entyear}</td>
								<input type="hidden" name="entyear" value="${entyear}">
								<td>${student.no}</td>
								<input type="hidden" name="no" value="${no}">
								<td>${student.classNum}</td>
								<input type="hidden" name="classNum" value="${classNum}">
								<td>${student.name}</td>
								<input type="hidden" name=name value="${name}">
								<input type="text" name="point_${ 学生番号 }">
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<%-- ボタンエリア --%>
				<div class="mt-4">
					<button type="submit" class="btn btn-secondary">登録して終了</button>
				</div>
			</div>
	</c:forEach>

	</c:param>
</c:import>