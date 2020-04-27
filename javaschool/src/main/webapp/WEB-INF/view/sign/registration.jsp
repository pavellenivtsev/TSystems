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
                       placeholder="Username">
            </div>
            <c:if test="${(usernameError!=null)}">
                <div class="text-center" id="warning-message">${usernameError}</div>
            </c:if>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input class="form-control" type="password" id="password" name="password"
                       placeholder="Must have at least 5 characters">
            </div>
        </div>
        <div class="form-group">
            <label for="passwordConfirm" class="col-sm-2 control-label">Confirm your password</label>
            <div class="col-sm-10">
                <input class="form-control" type="password" id="passwordConfirm" name="passwordConfirm"
                       placeholder="The same as the password">
            </div>
        </div>
        <div class="form-group">
            <label for="firstName" class="col-sm-2 control-label">First name</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="firstName" name="firstName"
                       placeholder="First name">
            </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="col-sm-2 control-label">Last name</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="lastName" name="lastName"
                       placeholder="Last name">
            </div>
        </div>
        <div class="form-group">
            <label for="phoneNumber" class="col-sm-2 control-label">Phone number</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="phoneNumber" name="phoneNumber"
                       placeholder="Phone number">
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <input class="form-control" type="email" id="email" name="email"
                       placeholder="example@domain.com">
            </div>
        </div>
        <div class="form-group">
            <label for="address" class="col-sm-2 control-label">Address</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" id="address" name="address"
                       placeholder="Enter a location">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit" id="form_sign_up">Sign up</button>
            </div>
        </div>
        <input type="text" id="latitude" style="visibility: hidden" name="latitude">
        <input type="text" id="longitude" style="visibility: hidden" name="longitude">
        <div id="map"></div>
        <div id="infowindow-content">
            <img src="" width="16" height="16" id="place-icon">
            <span id="place-name" class="title"></span><br>
            <span id="place-address"></span>
        </div>
    </form>
    <br>
    <c:url value="/" var="home"/>
    <h3 class="text-center"><a href="${home}">Home page</a></h3>
</div>
<script>
    function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 3,
            center: {lat: 66.25, lng: 94.15}
        });
        var input = document.getElementById('address');
        var autocomplete = new google.maps.places.Autocomplete(input);

        // Set the data fields to return when the user selects a place.
        autocomplete.setFields(
            ['address_components', 'geometry', 'icon', 'name']);

        autocomplete.setTypes([]);

        var infowindow = new google.maps.InfoWindow();
        var infowindowContent = document.getElementById('infowindow-content');
        infowindow.setContent(infowindowContent);
        var marker = new google.maps.Marker({
            map: map,
            anchorPoint: new google.maps.Point(0, -29)
        });

        autocomplete.addListener('place_changed', function () {
            infowindow.close();
            marker.setVisible(false);
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                // User entered the name of a Place that was not suggested and
                // pressed the Enter key, or the Place Details request failed.
                window.alert("No details available for input: '" + place.name + "'");
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                map.fitBounds(place.geometry.viewport);
            } else {
                map.setCenter(place.geometry.location);
                map.setZoom(17);  // Why 17? Because it looks good.
            }
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);

            $('#latitude').val(marker.getPosition().lat());
            $('#longitude').val(marker.getPosition().lng());

            var address = '';
            if (place.address_components) {
                address = [
                    (place.address_components[0] && place.address_components[0].short_name || ''),
                    (place.address_components[1] && place.address_components[1].short_name || ''),
                    (place.address_components[2] && place.address_components[2].short_name || '')
                ].join(' ');
            }

            infowindowContent.children['place-icon'].src = place.icon;
            infowindowContent.children['place-name'].textContent = place.name;
            infowindowContent.children['place-address'].textContent = address;
            infowindow.open(map, marker);
        });
    }

    $(document).ready(function () {
        $("#address").change(function () {
            $('input[name="latitude"]').val("");
        });

    });

    $(function () {
        $("#form").validate({
            // Specify validation rules
            rules: {
                username: {
                    required: true,
                    minlength: 2
                },
                password: {
                    required: true,
                    minlength: 5
                },
                passwordConfirm: {
                    required: true,
                    equalTo: "#password"
                },
                firstName: {
                    required: true,
                },
                lastName: {
                    required: true,
                },
                phoneNumber: {
                    required: true,
                },
                email: {
                    required: true,
                },
                address: {
                    required: true,
                },
                latitude: {
                    required: true,
                },
            },
            // Specify validation error messages
            messages: {
                username: {
                    required: "Please enter username.",
                    minlength: "Your username must be at least 2 characters long."
                },
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
                latitude: "Enter the location correctly. "
            },
            errorPlacement: function (error, element) {
                if (element.is('#latitude')) {
                    // error.insertAfter("#address");
                } else {
                    element.after(error);
                }
            },
        });
    });
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATUc0x8f71SoXH6-0qlrD3YJYVBgQ9VS4&libraries=places&callback=initMap&language=en"
        async defer></script>
</body>
</html>
