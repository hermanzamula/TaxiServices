angular.module("map-front", ["google-maps", "drivers-back", 'trips-back'])
    .controller("maps-controller", ['$scope', '$rootScope', '$location', '$timeout', '$log', 'Coordinates', "Drivers", 'Trips',
        function ($scope, $rootScope, $location, $timeout, $log, Coordinates, Drivers, Trips) {

            // $scope.markerDetails = new DetailsPopUp('#markerDetails', updateMarkers);

            var center = {
                latitude: 50,
                longitude: 36.22
            };

            $scope.formData = {};

            angular.extend($scope, {
                map: {
                    center: center,
                    zoom: 11,
                    dragging: false,
                    bounds: {},
                    options: {
                        streetViewControl: false,
                        panControl: false,
                        mapTypeControlOptions: {mapTypeIds: [] }
                    },
                    latitude: Coordinates.currentLocation().latitude,
                    longitude: Coordinates.currentLocation().longitude,
                    markers: [],
                    events: {
                        'bounds_changed': function (mapModel) {
                            var center = mapModel.getCenter();
                            var northEast = mapModel.getBounds().getNorthEast();
                            var southWest = mapModel.getBounds().getSouthWest();
                            Coordinates.setCenter(center.lat(), center.lng());
                            Coordinates.setLeftCorner(northEast.lat(), northEast.lng());
                            Coordinates.setRightCorner(southWest.lat(), southWest.lng());
                        }
                    }
                }
            });

            /*
             Security.locationServerBaseUrl(function(response) {
             function updateMarkers() {
             var locationServerBaseUrl = response.value.replace("http://", "");
             DriversLocation.query({
             locationServerBaseUrl: locationServerBaseUrl,
             latitude: Coordinates.getCoords().center.latitude,
             longitude: Coordinates.getCoords().center.longitude,
             radius: Coordinates.getRadius()*/
            /*,
             limit: limit*/
            /*

             }, function (data) {
             $scope.map.markers = convertToMarkers(data);
             });
             }

             //TODO: make scheduler
             interval = setInterval(updateMarkers, 1000);
             updateMarkers();

             });
             */

            $scope.showTripDetails = function (data) {
                console.log(data);
                $rootScope.selectedTrip = data.origin;
                $("#selected-trip").modal("show");
            };

            var driverImg = {
                "free": '../img/taxi-free.png',
                "busy": '../img/taxi-busy.png'
            };

            function convertToMarkers(data) {

                function createIconSettings(status) {

                    var size = 32, iconSettings = {};

                    iconSettings.url = driverImg.free;
                    iconSettings.scaledSize = new google.maps.Size(size, size);
                    return iconSettings;
                }

                return $.map(data, function (item) {
                    var icon = createIconSettings();
                    return {
                        coords: {latitude: item.start.lat,
                            longitude: item.start.lng},
                        icon: icon,
                        title: item.name,
                        origin: item
                    }
                });

            }

            // Enable the new Google Maps visuals until it gets enabled by default.
            // See http://googlegeodevelopers.blogspot.ca/2013/05/a-fresh-new-look-for-maps-api-for-all.html
            google.maps.visualRefresh = true;

            Coordinates.setCenter($scope.map.center.latitude, $scope.map.center.longitude);
            Coordinates.setLeftCorner($scope.map.latitude, $scope.map.longitude);

            $scope.$on("initPosition", function (e, pos) {
                $scope.map.center = pos;
                $scope.$apply("map.center");
            });

            $scope.$on("toLocChoose", function (e, pos) {
                console.log(pos);
                $scope.formData.to = pos;
            });
            $scope.$on("fromLocChoose", function (e, pos) {
                console.log(pos);
                $scope.formData.from = pos;
            });

            $scope.search = function () {
                $scope.trips = Trips.query({startLat: $scope.formData.from.latitude,
                    startLng: $scope.formData.from.longitude,
                    endLat: $scope.formData.to.latitude,
                    endLng: $scope.formData.to.longitude,
                    radius: 100000}, function (data) {
                    $scope.map.markers = convertToMarkers(data);
                });
            }

        }]
)
    .directive('searchFrom', ['$timeout', function ($timeout) {

        var createInput = function (map) {
            var input = document.createElement("input");
            input.type = "text";
            input.className = "form-control angular-google-map-autocomplete from";
            input.placeholder = "Место отправления";
            map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
            return input;
        };

        return {
            restrict: 'E',
            transclude: true,
            require: '^googleMap',
            link: function (scope, element, attrs, mapCtrl) {
                $timeout(function () {
                    var map = mapCtrl.getMap();
                    map.mapTypeControl = false; //todo: code smell. refactor
                    var input = createInput(map);
                    var autocomplete = new google.maps.places.Autocomplete(input);
                    autocomplete.bindTo('bounds', map);
                    $(input).css("z-index", "100");
                    google.maps.event.addListener(autocomplete, 'place_changed', function () {
                        var place = autocomplete.getPlace();

                        if (place && place.geometry) {
                            if (place.geometry.viewport) {
                                map.fitBounds(place.geometry.viewport);
                            } else {
                                map.setCenter(place.geometry.location);
                                //map.setZoom(17);  // Why 17? Because it looks good.
                            }
                            var markerData = {
                                latitude: place.geometry.location.lat(),
                                longitude: place.geometry.location.lng(),
                                showWindow: false,
                                title: place.formatted_address
                            };
                            scope.$emit('fromLocChoose', markerData);

                        } else {
                            console.log("Cannot find this place: " + place.name); //Todo: add message
                        }
                    });
                });
            }
        }
    }])
    .directive('searchTo', ['$timeout', function ($timeout) {
        var createInput = function (map) {
            var input = document.createElement("input");
            input.type = "text";
            input.className = "form-control angular-google-map-autocomplete to";
            input.placeholder = "Место прибытия";
            map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
            return input;
        };

        return {
            restrict: 'E',
            transclude: true,
            require: '^googleMap',
            link: function (scope, element, attrs, mapCtrl) {
                $timeout(function () {
                    var map = mapCtrl.getMap();
                    var input = createInput(map);
                    var autocomplete = new google.maps.places.Autocomplete(input);
                    autocomplete.bindTo('bounds', map);
                    $(input).css("z-index", "100");

                    google.maps.event.addListener(autocomplete, 'place_changed', function () {
                        var place = autocomplete.getPlace();

                        if (place && place.geometry) {
                            /* if (place.geometry.viewport) {
                             map.fitBounds(place.geometry.viewport);
                             } else {
                             map.setCenter(place.geometry.location);
                             map.setZoom(17);  // Why 17? Because it looks good.
                             }*/
                            var markerData = {
                                latitude: place.geometry.location.lat(),
                                longitude: place.geometry.location.lng(),
                                showWindow: false,
                                title: place.formatted_address
                            };
                            scope.$emit('toLocChoose', markerData);

                        } else {
                            console.log("Cannot find this place: " + place.name); //Todo: add message
                        }
                    });
                });
            }
        }
    }])
    .directive("mapHeader", ['$timeout', function ($timeout) {

        return {
            restrict: 'A',
            require: '^googleMap',
            link: function (scope, element, attrs, mapCtrl) {
                $timeout(function () {
                    var map = mapCtrl.getMap();
                    map.controls[google.maps.ControlPosition.TOP_LEFT].push(element[0]);
                });
            }
        }
    }])
    .service('Coordinates', function ($rootScope) {

        //Inspired by http://stackoverflow.com/questions/1502590/calculate-distance-between-two-points-in-google-maps-v3
        function distHaversine(p1, p2) {

            function rad(x) {
                return x * Math.PI / 180;
            }

            var R = 6371; // earth's mean radius in km
            var dLat = rad(p2.latitude - p1.latitude);
            var dLong = rad(p2.longitude - p1.longitude);

            var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rad(p1.latitude)) * Math.cos(rad(p2.latitude)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            var d = R * c;

            return d.toFixed(3) * 1000;
        }

        var coordinate = {};

        var currentLocation = {};

        var setPosition = function (position) {
            console.log("watch position: " + JSON.stringify(position));
            currentLocation.longitude = position.coords.longitude;
            currentLocation.latitude = position.coords.latitude;
        };

        navigator.geolocation.getCurrentPosition(function (position) {
            setPosition(position);
            $rootScope.$emit("initPosition", currentLocation);
        });
        navigator.geolocation.watchPosition(setPosition);

        return {
            currentLocation: function () {
                return currentLocation;
            },
            getCoords: function () {
                return coordinate;
            },
            setCoords: function (lt, lng) {
                coordinate.latitude = lt;
                coordinate.longitude = lng;
            },
            setCenter: function (lt, lng) {
                coordinate.center = {
                    latitude: lt,
                    longitude: lng
                }
            },
            setLeftCorner: function (lt, lng) {
                coordinate.corner = {
                    latitude: lt,
                    longitude: lng
                }
            },
            setRightCorner: function (lt, lng) {
                coordinate.cornerRight = {
                    latitude: lt,
                    longitude: lng
                }
            },
            getRadius: function () {
                if (coordinate.cornerRight) {
                    return (distHaversine(coordinate.cornerRight, coordinate.corner) / 2);
                }
                return distHaversine(coordinate.center, coordinate.corner)
            }
        };
    });

var DetailsPopUp = function (selector, onClose) {
    this.selector = selector;
    this.onClose = onClose;
};

DetailsPopUp.prototype.show = function () {
    $(this.selector).show();
};

DetailsPopUp.prototype.hide = function () {
    $(this.selector).hide();
    this.onClose();
};