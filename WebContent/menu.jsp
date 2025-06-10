<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<a href="${pageContext.request.contextPath}/main">exShop</a>
<a href="${pageContext.request.contextPath}/main/search">商品検索</a>


<c:choose>

    <%-- ログインしている場合（セッションにユーザー情報がある） --%>
    <c:when test="${not empty session_user}">
        <%-- セッション内のユーザーオブジェクト（例：User型）から username を表示 --%>
        <span>ようこそ、${session_user.username} さん</span>
        <a href="${pageContext.request.contextPath}/accounts/logout">ログアウト</a>
    </c:when>

    <%-- 未ログインの場合（セッションにユーザー情報がない） --%>
    <c:otherwise>
        <a href="${pageContext.request.contextPath}/accounts/login">ログイン</a>
        <a href="${pageContext.request.contextPath}/accounts/register">アカウント作成</a>
    </c:otherwise>

</c:choose>

<hr>
