<html>
<head>
    <%@include file="../common/common.jsp" %>
    <title>Order</title>
</head>
<body>
<%@include file="../common/navbar.jsp" %>
<div class="container" id="main-container">
    <h2 class="text-center">Order information</h2>
    <c:if test="${!(driver.truck==null)}">
        <c:choose>
            <c:when test="${driver.truck.userOrder!=null}">
                <div id="driver-map"></div>
                <c:set var="flag" value="${true}"/>
                <c:forEach var="cargo" items="${driver.truck.userOrder.cargoList}">
                    <c:if test="${(cargo.status.name().equals('PREPARED')||cargo.status.name().equals('SHIPPED'))}">
                        <c:set var="flag" value="${false}"/>
                    </c:if>
                </c:forEach>
                <div class="container">

                </div>
                <table class="table" id="cssTable2">
                    <tr>
                        <c:if test="${flag}">
                            <th rowspan="2"></th>
                        </c:if>
                        <th colspan="5">Cargo</th>
                        <th rowspan="2"></th>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <th>Weight</th>
                        <th>Place of departure</th>
                        <th>Delivery place</th>
                        <th>Status</th>
                    </tr>
                    <tr>
                        <c:if test="${flag}">
                            <td rowspan="${driver.truck.userOrder.cargoList.size()}">
                                <c:url value="/driver/order/status/completed" var="orderCompleted"/>
                                <form method="post" action="${orderCompleted}">
                                    <input type="hidden" name="userOrderId"
                                           value="${driver.truck.userOrder.id}">
                                    <button class="btn btn-default" type="submit">Back in the office</button>
                                </form>
                            </td>
                        </c:if>
                        <td>${driver.truck.userOrder.cargoList.get(0).name}</td>
                        <td>${driver.truck.userOrder.cargoList.get(0).weight}</td>
                        <td>${driver.truck.userOrder.cargoList.get(0).loadingAddress}</td>
                        <td>${driver.truck.userOrder.cargoList.get(0).unloadingAddress}</td>
                        <td>${driver.truck.userOrder.cargoList.get(0).status.name().toLowerCase().replaceAll("_"," ")}</td>
                        <td>
                            <c:if test="${driver.truck.userOrder.cargoList.get(0).status.name().equals('PREPARED')}">
                                <c:url value="/cargo/shipped" var="cargoShipped"/>
                                <form method="post" action="${cargoShipped}">
                                    <input type="hidden" name="id"
                                           value="${driver.truck.userOrder.cargoList.get(0).id}">
                                    <button class="btn btn-default" type="submit">Cargo is shipped</button>
                                </form>
                            </c:if>

                            <c:if test="${driver.truck.userOrder.cargoList.get(0).status.name().equals('SHIPPED')}">
                                <c:url value="/cargo/delivered" var="cargoDelivered"/>
                                <form method="post" action="${cargoDelivered}">
                                    <input type="hidden" name="id"
                                           value="${driver.truck.userOrder.cargoList.get(0).id}">
                                    <button class="btn btn-default" type="submit">Cargo is delivered</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                    <c:forEach var="cargo" items="${driver.truck.userOrder.cargoList}" varStatus="loop">
                        <c:if test="${not loop.first}">
                            <tr>
                                <td>${cargo.name}</td>
                                <td>${cargo.weight}</td>
                                <td>${cargo.loadingAddress}</td>
                                <td>${cargo.unloadingAddress}</td>
                                <td>${cargo.status.name().toLowerCase().replaceAll("_"," ")}</td>
                                <td>
                                    <c:if test="${cargo.status.name().equals('PREPARED')}">
                                        <c:url value="/cargo/shipped" var="cargoShipped"/>
                                        <form method="post" action="${cargoShipped}">
                                            <input type="hidden" name="id" value="${cargo.id}">
                                            <button class="btn btn-default" type="submit">Cargo is shipped</button>
                                        </form>
                                    </c:if>

                                    <c:if test="${cargo.status.name().equals('SHIPPED')}">
                                        <c:url value="/cargo/delivered" var="cargoDelivered"/>
                                        <form method="post" action="${cargoDelivered}">
                                            <input type="hidden" name="id" value="${cargo.id}">
                                            <button class="btn btn-default" type="submit">Cargo is delivered</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br>
                <div class="text-center">
                    You don't have an order yet, please wait
                </div>
                <br>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
<c:if test="${!(driver.truck==null)}">
    <c:if test="${driver.truck.userOrder!=null}">
        <script>
            var truck = JSON.parse('${truck}');
            var cargoList = truck.userOrder.cargoList;
            var waypts = [];
            var cargoPairList = [];
            cargoList.forEach((cargo, i) => {
                cargoPairList.push(new CargoPair(cargo));
            });

            let index = cargoPairList.length - 1;
            while (index >= 0) {
                if (cargoPairList[index].cargo.status === 'DELIVERED') {
                    cargoPairList.splice(index, 1);
                }
                index -= 1;
            }

            var currentPoint = new CurrentPoint(truck.address, truck.latitude, truck.longitude);
            var isUnloadedList = [];

            var cargo = {
                name: "minDistanceCargo",
                status: "PREPARED",
            };
            var minDistanceCargo = new CargoPair(cargo);
            minDistanceCargo.distanceToCurrentPoint = 40100;

            while (cargoPairList.length > 0) {
                for (let i = 0; i < cargoPairList.length; i++) {
                    if (cargoPairList[i].isUnloaded) {
                        cargoPairList.splice(i, 1);
                        continue;
                    }
                    if (cargoPairList[i].isLoaded) {
                        cargoPairList[i].distanceToCurrentPoint = getDistanceLength(currentPoint.latitude, currentPoint.longitude,
                            cargoPairList[i].cargo.unloadingLatitude, cargoPairList[i].cargo.unloadingLongitude);
                    } else {
                        cargoPairList[i].distanceToCurrentPoint = getDistanceLength(currentPoint.latitude, currentPoint.longitude,
                            cargoPairList[i].cargo.loadingLatitude, cargoPairList[i].cargo.loadingLongitude);
                    }
                    if (minDistanceCargo.distanceToCurrentPoint > cargoPairList[i].distanceToCurrentPoint) {
                        minDistanceCargo = cargoPairList[i];
                    }
                }

                if (cargoPairList.lenth === 0) {
                    break;
                }

                if (minDistanceCargo.isLoaded) {
                    minDistanceCargo.isUnloaded = true;
                    //add unloadingAddress
                    waypts.push({
                        location: minDistanceCargo.cargo.unloadingAddress,
                        stopover: true
                    });
                    currentPoint.address = minDistanceCargo.cargo.unloadingAddress;
                    currentPoint.latitude = minDistanceCargo.cargo.unloadingLatitude;
                    currentPoint.longitude = minDistanceCargo.cargo.unloadingLongitude;
                    isUnloadedList.push(true);
                } else {
                    minDistanceCargo.isLoaded = true;
                    //add loadingAddress
                    waypts.push({
                        location: minDistanceCargo.cargo.loadingAddress,
                        stopover: true
                    });
                    currentPoint.address = minDistanceCargo.cargo.loadingAddress;
                    currentPoint.latitude = minDistanceCargo.cargo.loadingLatitude;
                    currentPoint.longitude = minDistanceCargo.cargo.loadingLongitude;
                    isUnloadedList.push(false);
                }
                minDistanceCargo.distanceToCurrentPoint = 40100;
            }

            waypts.forEach((item, i) => {
                if (item.location === undefined) {
                    waypts.splice(i, 1);
                }
            });

            function CargoPair(cargo) {
                this.cargo = cargo;
                this.distanceToCurrentPoint = 0;
                this.isLoaded = (cargo.status === "SHIPPED");
                this.isUnloaded = (cargo.status === "DELIVERED");
            }

            function CurrentPoint(address, latitude, longitude) {
                this.address = address;
                this.latitude = latitude;
                this.longitude = longitude;
            }

            function getDistanceLength(lat2, lon2, lat1, lon1) {
                Number.prototype.toRad = function () {
                    return this * Math.PI / 180;
                };

                let R = 6371; // km
                let x1 = lat2 - lat1;
                let dLat = x1.toRad();
                let x2 = lon2 - lon1;
                let dLon = x2.toRad();
                let a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(lat1.toRad()) * Math.cos(lat2.toRad()) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                return R * c;
            }

            function initMap() {
                var directionsService = new google.maps.DirectionsService;
                var map = new google.maps.Map(document.getElementById('driver-map'), {
                    zoom: 2.5,
                    center: {lat: 66.25, lng: 94.15}
                });

                $(document).ready(function () {
                    directionsService.route({
                        origin: new google.maps.LatLng(truck.latitude, truck.longitude),
                        destination: new google.maps.LatLng(truck.office.latitude, truck.office.longitude),
                        waypoints: waypts,
                        optimizeWaypoints: false,
                        travelMode: 'DRIVING'
                    }, function (response, status) {
                        if (status === 'OK') {
                            new google.maps.DirectionsRenderer({
                                map: map,
                                directions: response,
                                suppressMarkers: true
                            });
                            var route = response.routes[0];

                            makeMarker(route.legs[0].start_location, icons.start, map);
                            makeMarker(route.legs[route.legs.length - 1].end_location, icons.end, map);
                            for (let i = 1; i < route.legs.length; i++) {
                                if (!isUnloadedList[i - 1]) {
                                    makeMarker(route.legs[i].start_location, icons.loading, map);
                                } else {
                                    makeMarker(route.legs[i].start_location, icons.unloading, map);
                                }
                            }
                        } else {
                            window.alert('Directions request failed due to ' + status);
                        }
                    });
                });
            }

            function makeMarker(position, icon, map) {
                new google.maps.Marker({
                    position: position,
                    map: map,
                    icon: icon,
                });
            }

            var icons = {
                start: {
                    url: 'http://maps.google.com/mapfiles/kml/shapes/truck.png',
                },
                loading: {
                    url: '${pageContext.request.contextPath}/resources/img/load-cargo.png',
                },
                unloading: {
                    url: '${pageContext.request.contextPath}/resources/img/unload-cargo.png',
                },
                end: {
                    url: 'http://maps.google.com/mapfiles/kml/shapes/homegardenbusiness.png',
                },
            }
        </script>
        <script async defer
                src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATUc0x8f71SoXH6-0qlrD3YJYVBgQ9VS4&callback=initMap">
        </script>
    </c:if>
</c:if>
</body>
</html>
