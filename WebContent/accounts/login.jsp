<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../css/style.css">
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />

<c:import url="/base.jsp">
  <c:param name="body">
  	<%-- ログインフォームの適応範囲 --%>
    <main class="form-signin" style="max-width: 500px; padding-top: 40px; margin: 0 auto; padding: 2rem;">
      <form id="loginForm" method="post" action="/JavaSD/accounts/loginexe">

        <h1 class="h3 mb-3 fw-bold text-center" style="background-color: #f0f0f0;">ログイン</h1>
        <ul>
        	<li style="list-style: none;">${ errorMessage }</li>
        </ul>

        <div class="form-floating mb-2">
          <input type="text" class="form-control" id="floatingInput" name="userId" value="${ userId }" required>
          <label for="floatingInput">ID</label>
        </div>

        <div class="form-floating mb-2">
          <input type="password" class="form-control" id="floatingPassword" name="password" required>
          <label for="floatingPassword">パスワード</label>
        </div>

        <div class="form-check my-3 d-flex justify-content-center">
          <input class="form-check-input" type="checkbox" id="showPassword">
          <label class="form-check-label" for="showPassword">
            パスワードを表示
          </label>
        </div>

        <div class="text-center">
            <button class="btn btn-primary w-50 py-2" style="max-width: 300px;" type="submit">ログイン</button>
        </div>
      </form>
    </main>

    <script>
      // パスワード表示切替
      document.getElementById('showPassword').addEventListener('change', function () {
        const pw = document.getElementById('floatingPassword');
        pw.type = this.checked ? 'text' : 'password';
      });

    </script>
  </c:param>
</c:import>