<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Cargo transportation</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="${pageContext.request.contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('MANAGER')">
                    <li><a href="${pageContext.request.contextPath}/dispatcher/cabinet"><i class="far fa-user"></i> Cabinet</a></li>
                    <li><a href="${pageContext.request.contextPath}/orders/completed">Completed orders</a></li>
                    <li><a href="${pageContext.request.contextPath}/orders/in-progress">Orders in progress</a></li>
                    <li><a href="${pageContext.request.contextPath}/orders/not-taken" id="not_taken_orders">Not taken orders</a></li>
                    <li><a href="${pageContext.request.contextPath}/office/all" id="offices"><i class="fas fa-building"></i> Offices</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('DRIVER')">
                    <li><a href="${pageContext.request.contextPath}/driver/cabinet"><i class="far fa-user"></i> Cabinet</a></li>
                    <li><a href="${pageContext.request.contextPath}/driver/truck"><i class="fas fa-truck-moving"></i> Truck</a></li>
                    <li><a href="${pageContext.request.contextPath}/driver/order"><i class="fas fa-file-alt"></i> Order</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    <li><a href="${pageContext.request.contextPath}/user/cabinet"><i class="far fa-user"></i> Cabinet</a></li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/login" id="login"><i class="fas fa-sign-in-alt"></i> Login</a></li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/registration"><i class="fas fa-user-plus"></i> Sign up</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/logout" id="logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</div>
<br><br><br><br><br>