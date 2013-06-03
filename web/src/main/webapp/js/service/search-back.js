angular.module('search-back', ['ngResource'])
    .factory('Search', function ($resource) {
        return $resource("../search/:kind");
    });