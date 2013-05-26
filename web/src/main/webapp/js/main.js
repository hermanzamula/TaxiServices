angular.module('taxi-service',['users', 'taxi-front'])
    .config(function($routeProvider){
        $routeProvider.
            when('/taxi/list', {controller: 'taxi-list', templateUrl: '../pages/board.html'}).
            when('/', {templateUrl: '../pages/main.html'}).
            otherwise({redirectTo: '/'});
    })
    .controller("main", function($scope, Users) {
        var request = {};
        request.name = "herman";
        request.lastName = "zamula";
        request.email = "herman@com";
        request.password = "pwd";

        $scope.click =  function () {
            Users.save(request, function(response) {
                console.log(response);
            })
        }
    });