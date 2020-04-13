<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Cargo transportation</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="/cabinet">Cabinet</a></li>
                    <li><a href="/admin/user/all">All users</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('MANAGER')">
                    <li><a href="/cabinet">Cabinet</a></li>
                    <li><a href="/order/all">All orders</a></li>
                    <li><a href="/office/all">All offices</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('DRIVER')">
                    <li><a href="/cabinet">Cabinet</a></li>
                    <li><a href="/driver/truck">Your truck</a></li>
                    <li><a href="/driver/order">Order</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    <li><a href="/cabinet">Cabinet</a></li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="/login">Login</a></li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="/registration">Sign up</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="/logout">Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</div>
<br><br><br><br><br>