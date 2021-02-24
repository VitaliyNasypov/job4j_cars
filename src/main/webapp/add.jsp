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
    <script src="js/scriptAdd.js"></script>
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
<div class="container">
    <div class="shadow p-3 mb-5 bg-body rounded">
        <form action="<%=request.getContextPath()%>/add" method="post" enctype="multipart/form-data">
            <div class="container-auth">
                <h2 align="center">Sell Car</h2>
                <hr>
                <label for="price" class="form-label"><b>Price</b></label>
                <input type="number" class="form-control" id="price" name="price"
                       placeholder="Enter Price..." required>
                <label for="color" class="form-label"><b>Color</b></label>
                <input type="text" class="form-control" id="color" name="color"
                       placeholder="Enter Color..." required>
                <label for="mileage" class="form-label"><b>Mileage</b></label>
                <input type="number" class="form-control" id="mileage" name="mileage"
                       placeholder="Enter Mileage..." required>
                <label for="year" class="form-label"><b>Year manufacture</b></label>
                <input type="number" class="form-control" id="year" name="year"
                       placeholder="Enter Year..." required>
                <br> <br>
                <div id="select-engine"></div>
                <br>
                <div id="select-body"></div>
                <br>
                <div id="select-rudder"></div>
                <br>
                <div id="select-transmission"></div>
                <br>
                <div id="select-brand" onchange="OnSelectionChange (this)"></div>
                <br>
                <div id="select-model"></div>
                <br>
                <br>
                <label class="form-label"><b>Upload photo</b></label>
                <input class="form-control" type="file" size="50" name="file"><br>
                <input class="form-control" type="file" size="50" name="file"><br>
                <input class="form-control" type="file" size="50" name="file"><br>
                <input class="form-control" type="file" size="50" name="file"><br>
                <input class="form-control" type="file" size="50" name="file"><br>

                <hr>
                <div style="text-align:center;">
                    <%--                    <button type="submit" class="btn btn-primary" style="width: 50%; text-align: center;">Add</button>--%>
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </div>
        </form>
    </div>
</div>
<footer class="pt-4 my-md-5 pt-md-5 border-top"></footer>
</body>
</html>

