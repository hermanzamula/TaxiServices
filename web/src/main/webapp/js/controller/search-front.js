angular.module('search-front', ['search-back'])
    .controller("search-input", function ($scope, $location) {
        $scope.search = function (query) {
            $location.path('/taxi/search/' + query);
        };
    })
    .factory("highlightSearchQuery", function () {
        return function (query) {
            setTimeout(function () {
                $.each(query.split(" "), function (i, term) {
                    $(".main-taxi-view").highlight(term.trim());
                });
            }, 500);
        }
    })
    .factory('doSearch', function (highlightSearchQuery, Search) {
        return function ($scope, query, callback) {

            function search(query) {
                return Search.query({query: query, kind: "drivers"}, callback);
            }

            $scope.drivers = search(query);
        }
    })
    .controller('search-taxi', function ($scope, Search, $routeParams, doSearch) {
        doSearch($scope, $routeParams.query, function (drivers) {
            var cities = [];
            angular.forEach(drivers, function (driver) {
                 angular.forEach(driver.cities, function(city){
                      if(!_.some(cities, city))
                     cities =  cities.concat(driver.cities);
                 })

            });
            $scope.cities = cities;
        })
    })
    .directive('onEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.onEnter);
                    });

                    event.preventDefault();
                }
            });
        };
    });
