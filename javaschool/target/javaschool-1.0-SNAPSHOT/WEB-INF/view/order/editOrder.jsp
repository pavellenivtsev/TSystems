<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Edit order</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/order/edit" var="var"/>
    <form name="editOrder" action="${var}" method="post" id="form">
        <h2 class="text-center">Edit order</h2>
        <div class="form row">
            <input type="hidden" name="orderId" id="id" class="form-control" value="${order.id}" required/>
        </div>
        <div class="form row">
            <label for="locationFromCity" class="col-sm-2 col-form-label">From city</label>
            <div class="col-sm-10">
                <input type="text" id="locationFromCity" name="locationFromCity" class="form-control"
                       value="${order.waypointList.get(0).location.city}" required/>
            </div>
        </div>

        <div class="form row">
            <label for="locationToCity" class="col-sm-2 col-form-label">To city</label>
            <div class="col-sm-10">
                <input type="text" id="locationToCity" name="locationToCity" class="form-control"
                       value="${order.waypointList.get(1).location.city}" required/>
            </div>
        </div>
        <input type="hidden" id="latitudeFrom" name="latitudeFrom"
               value="${order.waypointList.get(0).location.latitude}" required>
        <input type="hidden" id="longitudeFrom" name="longitudeFrom"
               value="${order.waypointList.get(0).location.longitude}" required>

        <input type="hidden" id="latitudeTo" name="latitudeTo" value="${order.waypointList.get(1).location.latitude}"
               required>
        <input type="hidden" id="longitudeTo" name="longitudeTo" value="${order.waypointList.get(1).location.latitude}"
               required>
        <input type="text" style="visibility: hidden" id="distance" name="distance" value="${order.distance}" required>

        <div class="text-center">
            <input id="submit" type="button" formaction="#" value="Build a route">
        </div>
        <div id="buildRoadError" class="text-center"></div>
        <div id="map"></div>

        <div class="text-center">
            <input type="submit" value="Edit order">
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
            origin: document.getElementById('locationFromCity').value,
            destination: document.getElementById('locationToCity').value,
            waypoints: waypts,
            optimizeWaypoints: true,
            travelMode: 'DRIVING'
        }, function (response, status) {
            if (status === 'OK') {
                directionsRenderer.setDirections(response);
                var route = response.routes[0];
                for (var i = 0; i < route.legs.length; i++) {
                    document.getElementById('distance').value = route.legs[i].distance.value;
                    document.getElementById('latitudeFrom').value = route.legs[i].start_location.lat();
                    document.getElementById('longitudeFrom').value = route.legs[i].start_location.lng();
                    document.getElementById('latitudeTo').value = route.legs[i].end_location.lat();
                    document.getElementById('longitudeTo').value = route.legs[i].end_location.lng();
                }
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });
    }

    $(function () {
        var validator = $("#form").validate({
            // Specify validation rules
            rules: {
                distance: "required",
            },
            // Specify validation error messages
            messages: {
                distance: "You need to confirm your location"
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
