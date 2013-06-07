angular.module('users-back', ['ngResource'])
    .factory("Users", function($resource){
        return  $resource("../user/:path", {}, {
            login: {method: 'POST', params: {path: "login"}},
            logout: {method: 'POST', params: {path: 'logout'}}
        });
    });