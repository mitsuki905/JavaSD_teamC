<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="../css/style.css">
<c:import url="/base.jsp">

	<c:param name="body">

		<h2 style="background-color: #f0f0f0;">　科目情報削除</h2>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="${pageContext.request.contextPath}/subject/subject_delete_done" method="post">
			<br>

			<%-- 確認文 --%>
			<label>
				<p>「${ subject.name }(${ subject.cd })」を削除してもよろしいですか</p>
			</label>

			<input type="hidden" name="cd" value="${subject.cd}">

			<%-- ボタンエリア --%>
			<div class="mt-4">
				<%-- 科目情報削除完了画面に遷移 --%>
				<button type="submit" class="btn btn-danger">削除</button>
				<br>
				<br>
				<%-- 科目管理一覧画面に遷移 --%>
				<a href="${pageContext.request.contextPath}/subject/subject_list" class="ms-3">戻る</a>
			</div>

			</form>
		</div>
	</c:param>

</c:import>