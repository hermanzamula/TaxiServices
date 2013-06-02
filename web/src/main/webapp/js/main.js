angular.module('taxi-service',['users-back', 'taxi-front'])
    .config(function($routeProvider){
        var detailsController = {controller: 'taxi-details', templateUrl: '../pages/taxi-info.html'};
        $routeProvider.
            when('/taxi/list/:city/', {controller: 'taxi-city-list', templateUrl: '../pages/list.html'}).
            when('/taxi/details/:city/:id/', detailsController).
            when('/taxi/details/:id', detailsController).
            when('/', {templateUrl: '../pages/main.html'}).
            otherwise({redirectTo: '/'});
    })
    .controller("main", function($scope, Users, Comments) {
        $scope.topComments = Comments.query();

        $scope.click =  function () {
            Users.save(request, function(response) {
                console.log(response);
            })
        }
    })
    .filter('truncate', function () {
        return function (input, symbols) {
            if (input && input.length > symbols) {
                return input.substring(0, symbols) + "...";
            }
            return input;
        }
    });