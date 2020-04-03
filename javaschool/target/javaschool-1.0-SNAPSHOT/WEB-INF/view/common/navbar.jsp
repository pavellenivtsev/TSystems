<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Cargo transportation</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="/">Home</a></li>
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="/cabinet">Cabinet</a></li>
                    <li><a href="/admin/user/all">All users</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('MANAGER')">
                    <li><a href="/cabinet">Cabinet</a></li>
                    <li><a href="/order/all">All orders</a></li>
                    <li><a href="/truck/all">All trucks</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('DRIVER')">
                    <li><a href="/cabinet">Cabinet</a></li>
                    <li><a href="/driver/information">Your information</a></li>
                    <li><a href="/driver/truck">Your truck</a></li>
                    <li><a href="/driver/order">Order</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    <li><a href="/cabinet">Cabinet</a></li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li>
                        <form action="/login" method="get">
                            <input value="Login" type="submit">
                        </form>
                    </li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li>
                        <form action="/registration" method="get">
                            <input value="Sign up" type="submit">
                        </form>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <form action="/logout" method="post">
                            <input value="Logout" type="submit">
                        </form>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</div>
<br><br><br><br><br>