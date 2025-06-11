<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">学生管理アプリ-login</c:param>

	<c:param name="body">

		<form action="login" method="post">

      			<%-- 学生番号の入力（必須） --%>
      			<label>id：</label>
      			<input type="text" name="id" required><br>

      			<%-- 学生名の入力（必須） --%>
      			<label>password：</label>
      			<input type="text" name="password" required><br>

      			<%-- 送信ボタン --%>
      			<input type="submit" value="送信">
   		 	</form>

	</c:param>

</c:import>