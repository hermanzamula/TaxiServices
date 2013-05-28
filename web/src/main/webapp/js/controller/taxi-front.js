angular.module('taxi-front', ['taxi-back'])
    .controller('taxi-city-list', function($scope, $routeParams, Drivers, Cities) {
        $scope.city = $routeParams.city;
        $scope.drivers = Drivers.city({id: $routeParams.city});
        $scope.cities = Cities.byCountry({id: 1})
    })
    .controller('taxi-details', function($scope, $routeParams, Drivers) {
        $scope.details = Drivers.details({id: $routeParams.id});
        $scope.driversByCity = Drivers.short({id: $routeParams.city});
        $scope.city = $routeParams.city;
    });
