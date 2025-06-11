<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../css/style.css">
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />

<c:import url="/base.jsp">
  <c:param name="body">
    <main class="form-signin" style="max-width: 500px; padding-top: 40px; margin: 0 auto; padding: 2rem;">
      <form id="loginForm" method="post" action="/accounts/login">
        <h1 class="h3 mb-3 fw-bold text-center">ログイン</h1>
        <li style="list-style: none;">${ errorMessage }</li>

        <div class="form-floating mb-2">
          <input type="text" class="form-control" id="floatingInput" name="userId" value="${ login }">
          <label for="floatingInput">ID</label>
        </div>

        <div class="form-floating mb-2">
          <input type="password" class="form-control" id="floatingPassword" name="password">
          <label for="floatingPassword">パスワード</label>
        </div>

        <div class="form-check text-start my-3">
          <input class="form-check-input" type="checkbox" id="showPassword">
          <label class="form-check-label" for="showPassword">
            パスワードを表示
          </label>
        </div>

        <button class="btn btn-primary w-100 py-2" type="submit">ログイン</button>
      </form>
    </main>

    <script>
      // パスワード表示切替
      document.getElementById('showPassword').addEventListener('change', function () {
        const pw = document.getElementById('floatingPassword');
        pw.type = this.checked ? 'text' : 'password';
      });

      // フォーム送信前にバリデーション
      document.getElementById('loginForm').addEventListener('submit', function (e) {
        const userId = document.getElementById('floatingInput');
        const password = document.getElementById('floatingPassword');

        // 以前のエラーを削除
        document.querySelectorAll('.error-msg').forEach(el => el.remove());

        let hasError = false;

        if (userId.value.trim() === '') {
          showError(userId, 'このフィールドを入力してください。');
          hasError = true;
        }

        if (password.value.trim() === '') {
          showError(password, 'このフィールドを入力してください。');
          hasError = true;
        }

        if (hasError) {
          e.preventDefault(); // 送信を中止
        }

        function showError(inputElement, message) {
          const error = document.createElement('div');
          error.className = 'error-msg text-danger mt-1';
          error.textContent = message;
          inputElement.parentNode.appendChild(error);
        }
      });
    </script>
  </c:param>
</c:import>
