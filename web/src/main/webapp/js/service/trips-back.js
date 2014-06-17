angular.module('trips-back', ['ngResource'])
    .factory('Trips', function ($resource) {
        return $resource("../trips/:id", {id: "@id"});
    });