angular.module('vdoroge-app',['google-maps'])
/*    .config(function($routeProvider){
        var detailsController = {controller: 'taxi-details', templateUrl: '../pages/taxi-info.html'};
        $routeProvider.
            when('/taxi/list/:city/', {controller: 'taxi-city-list', templateUrl: '../pages/list.html'}).
            when('/taxi/details/:city/:id/', detailsController).
            when('/taxi/details/:id', detailsController).
            when('/taxi/search/:query', {controller: 'search-taxi', templateUrl: '../pages/list.html'}).
            when('/', {templateUrl: '../pages/main.html'}).
            otherwise({redirectTo: '/'});
    })*/
    .controller("main", function($scope) {
        $scope.map = {
            center: {
                latitude: 45,
                longitude: -73
            },
            zoom: 8
        };

    /*    topCommentsScheduler($scope);
        $scope.isUserLogged = isUserLogged;
        $scope.logout = function() {
            Users.logout($.cookie(TOKEN));
            $.cookie(TOKEN, null);
        }*/
    })/*
    .filter('truncate', function () {
        return function (input, symbols) {
            if (input && input.length > symbols) {
                return input.substring(0, symbols) + "...";
            }
            return input;
        }
    })
    .factory('topCommentsScheduler', function (Comments) {
        return function ($scope) {

        }
    })*/
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
                                latitude: place.geometry.location.nb,
                                longitude: place.geometry.location.ob,
                                showWindow: false,
                                title: place.formatted_address
                            };
                            scope.$emit('addMarker', markerData);

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
                            if (place.geometry.viewport) {
                                map.fitBounds(place.geometry.viewport);
                            } else {
                                map.setCenter(place.geometry.location);
                                map.setZoom(17);  // Why 17? Because it looks good.
                            }
                            var markerData = {
                                latitude: place.geometry.location.nb,
                                longitude: place.geometry.location.ob,
                                showWindow: false,
                                title: place.formatted_address
                            };
                            scope.$emit('addMarker', markerData);

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
    }]);

