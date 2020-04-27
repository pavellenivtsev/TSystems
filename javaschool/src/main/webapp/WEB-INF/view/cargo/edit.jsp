<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Edit cargo</title>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/additional-methods.min.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <c:url value="/cargo/edit" var="var"/>
    <form action="${var}" method="post" id="form" class="form-horizontal">
        <h2 class="text-center">Edit cargo</h2>
        <input type="hidden" name="id" value="${cargo.id}">
        <div class="form-group">
            <label for="name" class="col-sm-2 col-form-label">Cargo name</label>
            <div class="col-sm-10">
                <input type="text" id="name" name="name" class="form-control"
                       value="${cargo.name}"
                       placeholder="Cargo name"
                       required/>
            </div>
        </div>
        <div class="form-group">
            <label for="weight" class="col-sm-2 col-form-label">Cargo weight, kg</label>
            <div class="col-sm-10">
                <input type="text" id="weight" name="weight" class="form-control"
                       value="${cargo.weight}"
                       pattern="[0-9]+(\.[0-9]+)?"
                       placeholder="Integer or decimal number" required/>
            </div>
        </div>
        <div class="form-group">
            <label for="loadingAddress" class="col-sm-2 col-form-label">Loading address</label>
            <div class="col-sm-10">
                <input type="text" id="loadingAddress" name="loadingAddress" class="form-control"
                       value="${cargo.loadingAddress}"
                       placeholder="Loading address"/>
            </div>
        </div>

        <div class="form-group">
            <label for="unloadingAddress" class="col-sm-2 col-form-label">Unloading address</label>
            <div class="col-sm-10">
                <input type="text" id="unloadingAddress" name="unloadingAddress" class="form-control"
                       value="${cargo.unloadingAddress}"
                       placeholder="Unloading address"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Edit cargo</button>
            </div>
        </div>
        <input type="text" style="visibility: hidden" id="loadingLatitude" name="loadingLatitude"
               value="${cargo.loadingLatitude}" required>
        <input type="hidden" id="loadingLongitude" name="loadingLongitude"
               value="${cargo.loadingLongitude}">
        <input type="text" style="visibility: hidden" id="unloadingLatitude" name="unloadingLatitude"
               value="${cargo.unloadingLatitude}" required>
        <input type="hidden" id="unloadingLongitude" name="unloadingLongitude"
               value="${cargo.unloadingLongitude}">
        <div id="map"></div>
    </form>
</div>
<script>
    var map;
    var markers = [];

    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            mapTypeControl: false,
            zoom: 3,
            center: {lat: 66.25, lng: 94.15},
        });

        new AutocompleteDirectionsHandler(map);
    }

    /**
     * @constructor
     */
    function AutocompleteDirectionsHandler(map) {
        this.map = map;
        this.originPlaceId = null;
        this.destinationPlaceId = null;
        this.travelMode = 'DRIVING';
        this.directionsService = new google.maps.DirectionsService;
        this.directionsRenderer = new google.maps.DirectionsRenderer({suppressMarkers: true});
        this.directionsRenderer.setMap(map);

        // loadingAddress
        var originInput = document.getElementById('loadingAddress');
        // unloadingAddress
        var destinationInput = document.getElementById('unloadingAddress');
        // var modeSelector = document.getElementById('mode-selector');

        var originAutocomplete = new google.maps.places.Autocomplete(originInput);
        // Specify just the place data fields that you need.
        originAutocomplete.setFields(['place_id']);

        var destinationAutocomplete =
            new google.maps.places.Autocomplete(destinationInput);
        // Specify just the place data fields that you need.
        destinationAutocomplete.setFields(['place_id']);

        this.setupPlaceChangedListener(originAutocomplete, 'ORIG');
        this.setupPlaceChangedListener(destinationAutocomplete, 'DEST');
    }

    AutocompleteDirectionsHandler.prototype.setupPlaceChangedListener = function (
        autocomplete, mode) {
        var me = this;
        autocomplete.bindTo('bounds', this.map);

        autocomplete.addListener('place_changed', function () {
            var place = autocomplete.getPlace();

            if (!place.place_id) {
                window.alert('Please select an option from the dropdown list.');
                return;
            }
            if (mode === 'ORIG') {
                me.originPlaceId = place.place_id;
            } else {
                me.destinationPlaceId = place.place_id;
            }
            me.route();
        });
    };

    AutocompleteDirectionsHandler.prototype.route = function () {
        if (!this.originPlaceId || !this.destinationPlaceId) {
            return;
        }
        var me = this;

        //clear markers
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
        }

        this.directionsService.route(
            {
                origin: {'placeId': this.originPlaceId},
                destination: {'placeId': this.destinationPlaceId},
                travelMode: this.travelMode
            },
            function (response, status) {
                if (status === 'OK') {
                    me.directionsRenderer.setDirections(response);
                    makeMarker(response.routes[0].legs[0].start_location, icons.loading, map);
                    makeMarker(response.routes[0].legs[0].end_location, icons.unloading, map);
                    $('#loadingLatitude').val(response.routes[0].legs[0].start_location.lat());
                    $('#loadingLongitude').val(response.routes[0].legs[0].start_location.lng());
                    $('#unloadingLatitude').val(response.routes[0].legs[0].end_location.lat());
                    $('#unloadingLongitude').val(response.routes[0].legs[0].end_location.lng());
                } else {
                    window.alert('Directions request failed due to ' + status);
                }
            });
    };

    function makeMarker(position, icon, map) {
        var marker = new google.maps.Marker({
            position: position,
            map: map,
            icon: icon,
        });
        markers.push(marker);
    }

    var icons = {
        loading: {
            url: '../../../resources/img/load-cargo.png',
        },
        unloading: {
            url: '../../../resources/img/unload-cargo.png',
        },
    };

    $(document).ready(function () {
        $("#loadingAddress").change(function () {
            $('input[name="loadingLatitude"]').val("");
        });

    });
    $(document).ready(function () {
        $("#unloadingAddress").change(function () {
            $('input[name="unloadingLatitude"]').val("");
        });

    });

    $(function () {
        var validator = $("#form").validate({
            // Specify validation error messages
            messages: {
                loadingLatitude: "Enter the location correctly. ",
                unloadingLatitude: "Enter the location correctly. "
            },
            errorPlacement: function (error, element) {
                if (element.is("#loadingLatitude")||element.is("#unloadingLatitude")) {
                    if (element.is("#loadingLatitude")){
                        // error.insertAfter("#loadingAddress");
                    } else {
                        // error.insertAfter("#unloadingAddress");
                    }
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
