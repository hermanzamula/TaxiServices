angular.module('taxi-front', ['taxi-back'])
    .controller('taxi-city-list', function($scope, $routeParams, Drivers) {
        $scope.city = $routeParams.city;
        $scope.drivers = Drivers.city({id: $routeParams.city});
    })
    .controller('taxi-details', function($scope, $routeParams, Drivers) {
        $scope.details = Drivers.details({id: $routeParams.id});
        $scope.comments = Drivers.comments({id: $routeParams.id});
        $scope.driversByCity = Drivers.short({id: $routeParams.city});
        $scope.city = $routeParams.city;
    });
