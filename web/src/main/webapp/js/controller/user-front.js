 angular.module('users', ['ngResource']).
    factory('Users', function($resource) {
        return $resource('../user/:path');
    });

