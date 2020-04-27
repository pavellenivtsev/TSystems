<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Add new office</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/office/add" var="var"/>
    <form action="${var}" method="post" id="form" class="form-horizontal">
        <h2 class="text-center">Add new office</h2>
        <div class="form-group">
            <label for="title" class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-10">
                <input type="text" id="title" name="title" autofocus="autofocus" class="form-control"
                       placeholder="Name" required/>
            </div>
        </div>
        <div class="form-group">
            <label for="address" class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="text" id="address" name="address" class="form-control"
                       placeholder="Address"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Add office</button>
            </div>
        </div>
        <input type="text" style="visibility: hidden" id="latitude" name="latitude">
        <input type="text" style="visibility: hidden" id="longitude" name="longitude">
        <div id="map"></div>
        <div id="infowindow-content">
            <img src="" width="16" height="16" id="place-icon">
            <span id="place-name" class="title"></span><br>
            <span id="place-address"></span>
        </div>
    </form>
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
            toggleFields();
        });

    });

    function toggleFields() {
        $('input[name="latitude"]').val("");
    }

    $(function () {
        $('#form').validate({
            rules: {
                latitude: "required",
            },
            // Specify validation error messages
            messages: {
                latitude: "Enter the location correctly."
            },
            errorPlacement: function (error, element) {
                if (element.is("#latitude")) {
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
