<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../css/style.css">
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

<c:import url="/base1.jsp">
  <c:param name="body">


  <form action="main" method="get">
	  <main class="form-signin w-100 m-auto">
	    <h1 class="h3 mb-3 fw-normal">ログイン</h1>

	    <div class="form-floating">
	      <input type="text" class="form-control" id="ID" placeholder="ID">
	      <label for="ID">ID</label>
	    </div>
	    <div class="form-floating">
	      <input type="password" class="form-control" id="Password" placeholder="Password">
	      <label for="Password">パスワード</label>
	    </div>

	    <div class="form-check text-start my-3">
	      <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
	      <label class="form-check-label" for="flexCheckDefault">
	        パスワードを表示
	      </label>
	    </div>

	    <button class="btn btn-primary w-100 py-2" type="submit">ログイン</button>
	  </main>
	</form>

  </c:param>
</c:import>
