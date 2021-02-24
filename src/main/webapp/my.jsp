<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
            crossorigin="anonymous"></script>
    <script src="js/scriptMy.js"></script>
</head>
<body>
<header class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-body border-bottom shadow-sm">
    <p class="h5 my-0 me-md-auto fw-normal">Selling cars</p>
    <nav class="my-2 my-md-0 me-md-3">
        <a class="p-2 text-dark" href="<c:url value='/'/>">Buy car</a>
        <a class="p-2 text-dark" href="<c:url value='/add.jsp'/>">Sell car</a>
        <a class="p-2 text-dark" href="<c:url value='/my.jsp'/>">My ads</a>
        <a class="btn btn-outline-primary" href="<c:url value='/login?out=${true}'/>"><c:out value="${user.name}"/>
            | Logout</a>
    </nav>
</header>
<main class="container">
    <div class="row row-cols-1 row-cols-md-3 mb-3 text-center" id="row" onclick="changeAdStatus(event)">
    </div>
    <br>
    <footer class="pt-4 my-md-5 pt-md-5 border-top"></footer>
</main>
</body>
</html>

