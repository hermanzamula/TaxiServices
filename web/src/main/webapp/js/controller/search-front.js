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
        return function ($scope, query) {

            function search(query) {
                return Search.query({query: query, kind: "drivers"});
            }

            $scope.drivers = search(query);
        }
    })
    .controller('search-taxi', function ($scope, Search, $routeParams, doSearch) {
        if ($scope.pathError) return;
        doSearch($scope, $routeParams.query);
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
