<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<c:url value='/css/style.css'/>
<c:import url="/base.jsp">

	<c:param name="body">

		<h2>　科目情報登録</h2>

		<%-- サーバーからのエラーメッセージを表示する --%>
		<c:if test="${not empty error}">
			<div class="alert alert-warning mt-3" role="alert">
				<c:out value="${error}" />
			</div>
		</c:if>

		<%-- フォーム本体 --%>
		<div class="card-body">
			<form action="${pageContext.request.contextPath}/subject/subject_create_done" method="post">
			<br>

			<%-- 科目コード --%>
			<div class="mb-3">
				<label for="subjectCd" class="form-label">科目コード</label>
				<input
					type="text"
					class="form-control"
					id="subjectCd"
					name="cd"
					value="${cd}"
					maxlength="3"
					placeholder="科目コードを入力してください"
					required>

				<%-- 科目コード重複エラー --%>
				<%-- エラーカラー --%>
		        <li class="text-warning error-message-item">${ errorMessage }</li>
			</div>

			<%-- 科目名 --%>
			<div class="mb-3">
				<label for="subjectName" class="form-label">科目名</label>
				<input
					type="text"
					class="form-control"
					id="subjectName"
					name="name"
					value="${name}"
					maxlength="20"
					placeholder="科目名を入力してください"
					required>
			</div>

			<%-- ボタンエリア --%>
			<div class="mt-4">
				<button type="submit" class="btn btn-primary">登録</button>
				<br>
				<br>
				<%-- 科目管理一覧画面に遷移 --%>
				<a href="${pageContext.request.contextPath}/subject/subject_list" class="ms-3">戻る</a>
			</div>

			</form>
		</div>


		<%-- 文字数エラーはJSP側で判断 --%>
		<script>
		  document.querySelector('form').addEventListener('submit', function (e) {
		    const subjectCd = document.getElementById('subjectCd');
		    const subjectName = document.getElementById('subjectName');

		    // 既存のエラーをすべて削除
		    document.querySelectorAll('.error-msg').forEach(el => el.remove());

		    let hasError = false;

		    if (subjectCd.value.trim().length !== 3) {
	    	  showError(subjectCd, '科目コードは3文字で入力してください');
	    	  hasError = true;
		    }

		    if (hasError) {
		      e.preventDefault(); // フォーム送信を中止
		    }

		    function showError(inputElement, message) {
		      const error = document.createElement('div');
		      error.className = 'error-msg text-warning mt-1';
		      error.textContent = message;
		      inputElement.parentNode.appendChild(error);
		    }
		  });
		</script>


	</c:param>
</c:import>