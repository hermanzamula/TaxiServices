angular.module('vdoroge-app',['map-front','ui', 'ui.bootstrap', 'request-interceptor', 'validators'])
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

        if(!$.cookie(TOKEN)) {
            window.location.href = "../pages/login.html";
        }


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
;

