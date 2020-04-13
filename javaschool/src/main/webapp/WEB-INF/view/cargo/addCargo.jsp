<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Add cargo</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/cargo/add" var="var"/>
    <form action="${var}" method="post" id="form">
        <h2 class="text-center">Add new cargo</h2>
        <div class="form row">
            <label for="name" class="col-sm-2 col-form-label">Cargo name</label>
            <div class="col-sm-10">
                <input type="text" id="name" name="name" class="form-control"
                       placeholder="Cargo name"
                       required/>
            </div>
        </div>
        <div class="form row">
            <label for="weight" class="col-sm-2 col-form-label">Cargo weight, kg</label>
            <div class="col-sm-10">
                <input type="text" id="weight" name="weight" class="form-control"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <div class="form row">
            <label for="loadingAddress" class="col-sm-2 col-form-label">Loading address</label>
            <div class="col-sm-10">
                <input type="text" id="loadingAddress" name="loadingAddress" class="form-control"
                       placeholder="Loading address" required/>
            </div>
        </div>

        <div class="form row">
            <label for="unloadingAddress" class="col-sm-2 col-form-label">Unloading address</label>
            <div class="col-sm-10">
                <input type="text" id="unloadingAddress" name="unloadingAddress" class="form-control"
                       placeholder="Unloading address" required/>
            </div>
        </div>
        <input type="text" style="visibility: hidden" id="loadingLatitude" name="loadingLatitude" required>
        <input type="hidden" id="loadingLongitude" name="loadingLongitude">

        <input type="hidden" id="unloadingLatitude" name="unloadingLatitude">
        <input type="hidden" id="unloadingLongitude" name="unloadingLongitude">

        <div class="text-center">
            <button class="btn btn-default" id="submit" type="button" formaction="#">Build a route</button>
        </div>
        <div id="buildRoadError" class="text-center"></div>
        <div id="map"></div>
        <input type="hidden" name="orderId" value="${orderId}" required/>
        <div class="text-center">
            <button class="btn btn-default" type="submit">Add cargo</button>
        </div>
    </form>
</div>

<script>
    function initMap() {
        var directionsService = new google.maps.DirectionsService;
        var directionsRenderer = new google.maps.DirectionsRenderer;
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 2.5,
            center: {lat: 66.25, lng: 94.15}
        });
        directionsRenderer.setMap(map);

        document.getElementById('submit').addEventListener('click', function () {
            calculateAndDisplayRoute(directionsService, directionsRenderer);
        });
    }

    function calculateAndDisplayRoute(directionsService, directionsRenderer) {
        var waypts = [];
        directionsService.route({
            origin: document.getElementById('loadingAddress').value,
            destination: document.getElementById('unloadingAddress').value,
            waypoints: waypts,
            optimizeWaypoints: true,
            travelMode: 'DRIVING'
        }, function (response, status) {
            if (status === 'OK') {
                directionsRenderer.setDirections(response);
                var route = response.routes[0];
                for (var i = 0; i < route.legs.length; i++) {
                    document.getElementById('loadingLatitude').value = route.legs[i].start_location.lat();
                    document.getElementById('loadingLongitude').value = route.legs[i].start_location.lng();
                    document.getElementById('unloadingLatitude').value = route.legs[i].end_location.lat();
                    document.getElementById('unloadingLongitude').value = route.legs[i].end_location.lng();
                }
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });
    }

    $(function () {
        var validator = $("#form").validate({
            // Specify validation error messages
            messages: {
                loadingLatitude: "You need to build a road"
            },
            errorPlacement: function (error, element) {
                if (element.is('#distance')) {
                    error.appendTo("#buildRoadError")
                } else {
                    element.after(error);
                }
            },
        });
    });
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATUc0x8f71SoXH6-0qlrD3YJYVBgQ9VS4&callback=initMap">
</script>
</body>
</html>
