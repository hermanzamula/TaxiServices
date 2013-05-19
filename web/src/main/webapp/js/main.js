

angular.module('users', ['ngResource']).
    factory('Users', function($resource) {
        return $resource('../user/');
    });

angular.module('main',['users'],
    function ($locationProvider) {
//        $locationProvider.html5Mode(true);
    }).
    controller("main", function($scope, Users) {
        console.log(Users.get());
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