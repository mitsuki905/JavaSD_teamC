<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../css/style.css">
<!-- Bootstrap icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />

<c:import url="/base1.jsp">
  <c:param name="body">
    <main class="form-signin w-100 m-auto" style="max-width: 300px; padding-top: 40px;">
      <form method="post" action="${pageContext.request.contextPath}/login">
        <h1 class="h3 mb-3 fw-bold text-center">ログイン</h1>

        <div class="form-floating mb-2">
          <input type="text" class="form-control" id="floatingInput" name="userId" placeholder="admin">
          <label for="floatingInput">ID</label>
        </div>

        <div class="form-floating mb-2">
          <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password123">
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
    </script>
  </c:param>
</c:import>
