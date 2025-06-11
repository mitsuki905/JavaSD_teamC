<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
	<head>
	    <title>得点管理システム</title>
	    <style>
	        body {
	            font-family: sans-serif;
	        }

			/* ヘッダー部分 */
	        .header {
	            background: linear-gradient(to right, #f0f8ff, #e6f0ff);
	            padding: 15px 20px;
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	        }

	        .header h1 {
	            margin: 0;
	            font-size: 24px;
	            color: #333;
	        }

	        .user-info {
	            font-size: 14px;
	            text-align: right;
	        }

	        .user-info span {
	            margin-right: 10px;
	        }

	        .user-info a {
	            color: #007bff;
	            text-decoration: none;
	            text-decoration: underline;
	        }

	        .sidebar {
				position: fixed;
				top: 0;
				left: 0;
			    width: 25%;
			    padding: 20px;
			    border-right: 1px solid #000;
			    height: 100vh;
			    box-sizing: border-box;
			}
	    </style>
	</head>

	<body>
		<div class="header">
		    <h1>得点管理システム</h1>
		    <%-- <c:if test="${not empty session_user}"> --%>
		        <div class="user-info">
		            <%-- <span>${session_user} 様</span> --%>
		            <span>大原　太郎１様</span>
		            <a href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
		        </div>
		    <%-- </c:if> --%>
		</div>

		<c:import url="/menu.jsp"/>
		${ param.body }

    	<p>© 2025 TIC</p>
    	<p>大原学園</p>
	</body>
</html>