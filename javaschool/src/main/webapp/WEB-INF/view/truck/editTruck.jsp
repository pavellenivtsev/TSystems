<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common/common.jsp" %>
    <title>Edit truck</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/truck/edit" var="var"/>
    <form action="${var}" method="post" id="form">
        <h2 class="text-center">Edit the truck</h2>
        <c:if test="${(editTruckError!=null)}">
            <div class="text-center" id="warning-message">${editTruckError}</div>
        </c:if>
        <div class="form row">
            <input type="hidden" name="id" id="id" value="${truck.id}" class="form-control"/>
        </div>
        <div class="form row">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration number</label>
            <div class="col-sm-10">
                <input type="text" name="registrationNumber" id="registrationNumber" class="form-control"
                       value="${truck.registrationNumber}"
                       pattern="[a-zA-Z]{2}[0-9]{5}"
                       placeholder="AA12345" required/>
            </div>
        </div>
        <div class="form row">
            <label for="driverShiftSize" class="col-sm-2 col-form-label">Driver shift size, hours</label>
            <div class="col-sm-10">
                <input type="text" name="driverShiftSize" id="driverShiftSize" class="form-control"
                       value="${truck.driverShiftSize}"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <div class="form row">
            <label for="weightCapacity" class="col-sm-2 col-form-label">Weight capacity, kg</label>
            <div class="col-sm-10">
                <input type="text" name="weightCapacity" id="weightCapacity" class="form-control"
                       value="${truck.weightCapacity}"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <div class="form row">
            <label for="address" class="col-sm-2 col-form-label">City</label>
            <div class="col-sm-10">
                <input type="text" name="locationCity" id="address" class="form-control"
                       value="${truck.location.city}"
                       placeholder="City"required/>
            </div>
        </div>
        <input type="text" style="visibility: hidden" id="latitude" name="latitude" value="${truck.location.latitude}" required>
        <input type="text" style="visibility: hidden" id="longitude" name="longitude" value="${truck.location.longitude}">
        <div class="form-group">
            <div class="text-center">
                <input id="confirmLocation" type="button" formaction="#" value="Confirm location">
            </div>
        </div>
        <div id="confirmLocationError" class="text-center"></div>
        <div id="map"></div>
        <div class="text-center">
            <button type="submit">Edit truck</button>
        </div>
    </form>
</div>
<script>
    var markers = [];
    var map;
    var geocoder;

    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 2.5,
            center: {lat: 66.25, lng: 94.15}
        });
        geocoder = new google.maps.Geocoder();
        document.getElementById('confirmLocation').addEventListener('click', function () {
            geocodeAddress(geocoder, map);
        });
    }

    function geocodeAddress(geocoder, resultsMap) {
        var address = document.getElementById('address').value;
        geocoder.geocode({'address': address}, function (results, status) {
            if (status === 'OK') {
                resultsMap.setCenter(results[0].geometry.location);
                clearMarkers()
                var marker = new google.maps.Marker({
                    map: resultsMap,
                    position: results[0].geometry.location
                });
                $('#latitude').val(marker.getPosition().lat());
                $('#longitude').val(marker.getPosition().lng());
                markers.push(marker);
            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    }

    function setMapOnAll(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }

    function clearMarkers() {
        setMapOnAll(null);
    }

    $(function () {
        $('#form').validate({
            rules: {
                latitude: "required",
            },
            // Specify validation error messages
            messages: {
                latitude: "You need to confirm location"
            },
            errorPlacement: function (error, element) {
                if (element.is("#latitude")) {
                    error.appendTo("#confirmLocationError");
                }
                else {
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
