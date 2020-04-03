<!DOCTYPE html>
<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Registration</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>

<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Registration</h2>
    <c:url value="/registration" var="var"/>
    <form id="form" class="form-horizontal" name="registration" method="post" action="${var}">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="username" name="username" autofocus="autofocus"
                       placeholder="Username" required>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input class="form-control" type="password" id="password" name="password"
                       placeholder="Must have at least 5 characters" required>
            </div>
        </div>
        <div class="form-group">
            <label for="passwordConfirm" class="col-sm-2 control-label">Confirm your password</label>
            <div class="col-sm-10">
                <input class="form-control" type="password" id="passwordConfirm" name="passwordConfirm"
                       placeholder="The same as the password" required>
            </div>
        </div>
        <div class="form-group">
            <label for="firstName" class="col-sm-2 control-label">First name</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="firstName" name="firstName"
                       placeholder="First name" required>
            </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="col-sm-2 control-label">Last name</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="lastName" name="lastName"
                       placeholder="Last name" required>
            </div>
        </div>
        <div class="form-group">
            <label for="phoneNumber" class="col-sm-2 control-label">Phone number</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="phoneNumber" name="phoneNumber"
                       placeholder="Phone number" required>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input class="form-control" type="email" id="email" name="email"
                       placeholder="example@domain.com" required>
            </div>
        </div>
        <div class="form-group">
            <label for="address" class="col-sm-2 control-label">City</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="address" name="locationCity"
                       placeholder="City" required>
            </div>
        </div>
        <input type="text" style="visibility: hidden" id="latitude" name="latitude" required>
        <input type="text" style="visibility: hidden" id="longitude" name="longitude">
        <div class="form-group">
            <div class="text-center">
                <input id="confirmLocation" type="button" formaction="#" value="Confirm location">
            </div>
        </div>
        <div id="confirmLocationError" class="text-center"></div>
        <div id="map"></div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit">Sign up</button>
            </div>
        </div>
    </form>
</div>

<c:url value="/" var="home"/>
<h3 class="text-center"><a href="${home}">Home page</a></h3>
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
        var validator = $("#form").validate({
            // Specify validation rules
            rules: {
                // The key name on the left side is the name attribute
                // of an input field. Validation rules are defined
                // on the right side
                password: {
                    minlength: 5
                },
                passwordConfirm: {
                    equalTo: "#password"
                },
            },
            // Specify validation error messages
            messages: {
                username: "Please enter username.",
                password: {
                    required: "Please provide a password.",
                    minlength: "Your password must be at least 5 characters long."
                },
                passwordConfirm: {
                    required: "Please provide a password.",
                    equalTo: "Enter confirm password same as password."
                },
                firstName: "Please enter your first name.",
                lastName: "Please enter your last name.",
                phoneNumber: "Pleas enter a valid phone number.",
                locationCity: "Please enter your city.",
                email: "Please enter a valid email address.",
                latitude: "You need to confirm your location."
            },
            errorPlacement: function (error, element) {
                if (element.is('#latitude')) {
                    error.appendTo("#confirmLocationError")
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
