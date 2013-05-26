angular.module('taxi-front', ['taxi-back'])
    .controller('taxi-list', function($scope, Drivers) {
        $scope.drivers = Drivers.all();
    });
