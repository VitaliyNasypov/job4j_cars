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
    <script src="js/scriptIndex.js"></script>
</head>
<body>

<header class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-body border-bottom shadow-sm">
    <p class="h5 my-0 me-md-auto fw-normal">Selling cars</p>
    <nav class="my-2 my-md-0 me-md-3">
        <span hidden data-bs-toggle="modal" data-bs-target="#view" id="active-view"></span>
        <a class="p-2 text-dark" href="<c:url value='/'/>">Buy car</a>
        <c:choose>
        <c:when test="${user != null}">
        <a class="p-2 text-dark" href="<c:url value='/add.jsp'/>">Sell car</a>
        <a class="p-2 text-dark" href="<c:url value='/my.jsp'/>">My ads</a>
        <a class="btn btn-outline-primary" href="<c:url value='/login?out=${true}'/>"><c:out value="${user.name}"/>
            | Logout</a>
    </nav>
    </c:when>
    <c:when test="${user == null}">
        <a class="p-2 text-dark" href="#" data-bs-toggle="modal" data-bs-target="#login-modal">Login</a>
        </nav>
        <a class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#sign-up-modal">Sign up</a>
    </c:when>
    </c:choose>
</header>
<c:if test="${error != null}">
    <div class="alert alert-danger" role="alert" style="width: 40%; left: 30%; text-align:center;">
        <c:out value="${error}"/>
    </div>
</c:if>
<c:if test="${info != null}">
    <div class="alert alert-info" role="alert" style="width: 40%; left: 30%; text-align:center;">
        <c:out value="${info}"/>
    </div>
</c:if>
<main class="container">
    <div class="shadow-none p-3 mb-5 bg-light rounded">
        <div class="container">
            <div class="row">
                <div class="col">
                    <label id="brand" name="brand">Brand:&nbsp;&nbsp;</label>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col order-last">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" name="photo" checked id="photo">
                            <label class="form-check-label" name="photo"> Photo</label>
                            <button type="submit" class="btn btn-outline-primary" style="float: right"
                                    onclick="selectFilter()">Show
                            </button>
                        </div>
                    </div>
                    <div class="col order-first">
                        <label>Date range:&nbsp;&nbsp;</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="date" value="anytime" checked>
                            <label class="form-check-label" name="date"> Anytime</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="date" value="today">
                            <label class="form-check-label" name="date"> Today</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<main class="container">
    <div class="row row-cols-1 row-cols-md-3 mb-3 text-center" id="row" onclick="loadViewAd(event)">
    </div>
    <div class="btn-group" role="group" aria-label="Basic example" style="float: left;" name="previous"
         id="previous_1_0">
        <button type="button" class="btn btn-primary" onclick="previousPage()">Previous page</button>
    </div>
    <div class="btn-group" role="group" aria-label="Basic example" style="float: right;" name="next" id="next">
        <button type="button" class="btn btn-primary" onclick="nextPage()">Next page</button>
    </div>
    <br>
    <footer class="pt-4 my-md-5 pt-md-5 border-top"></footer>
</main>
<div class="modal fade" id="login-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="login-title">Login</h5>
            </div>
            <form action="<%=request.getContextPath()%>/login" method="post">
                <div class="modal-body">
                    <label for="login-email" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="login-email" aria-describedby="emailHelp"
                           name="login-email"
                           placeholder="Enter Email..." required>
                    <label for="login-password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="login-password" name="login-password"
                           placeholder="Enter Password..." required>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="sign-up-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="sign-up-title">Sign up</h5>
            </div>
            <form action="<%=request.getContextPath()%>/signup" method="post">
                <div class="modal-body">
                    <label for="signup-name" class="form-label">Name</label>
                    <input type="name" class="form-control" id="signup-name" name="signup-name"
                           placeholder="Enter Name..." required>
                    <label for="signup-phone" class="form-label">Phone</label>
                    <input type="tel" class="form-control" id="signup-phone" name="signup-phone"
                           placeholder="Enter Phone..." required>
                    <label for="signup-email" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="signup-email" aria-describedby="emailHelp"
                           name="signup-email"
                           placeholder="Enter Email..." required>
                    <label for="signup-password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="signup-password" name="signup-password"
                           placeholder="Enter Password..." required>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Sign up</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="view" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--            header-->
            <div class="modal-header">
                <h5 class="modal-title" id="model-title"></h5>
                <h5 class="modal-title" id="price-title"></h5>
            </div>
            <!--            image-->
            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner" id="images-ad">
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <!-- Parametr car -->
            <div class="container">
                <div class="row row-cols-4">
                    <div class="card" style="width: 50%;">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item" id="color"></li>
                            <li class="list-group-item" id="mileage"></li>
                            <li class="list-group-item" id="year"></li>
                            <li class="list-group-item" id="body"></li>
                        </ul>
                    </div>
                    <div class="card" style="width: 50%;">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item" id="engine"></li>
                            <li class="list-group-item" id="rudder"></li>
                            <li class="list-group-item" id="transmission"></li>
                            <li class="list-group-item" id="status"></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="card" style="width: 100%; text-align: center;">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item" id="phone"></li>
                </ul>
            </div>
            <div style="text-align:center;">
                <button type="button" class="btn btn-danger" style="width: 100%; text-align: center;"
                        data-bs-dismiss="modal" aria-label="Close">Close
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

