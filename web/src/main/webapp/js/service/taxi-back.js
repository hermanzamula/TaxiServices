 angular.module('drivers-back', ['ngResource'])
    .factory('Drivers', function($resource){
        return $resource('../drivers/:filter/:id/:path', {id: "@id"}, {
            'all': {method: 'GET', isArray: true},
            'details': {method: 'GET'},
            'comments': {method: 'GET', isArray: true, params: {filter: 'comments'}},
            'comment': {method: "POST", params: {filter: 'comment'}}
        });
    })
     .factory('DriversLocation', function($resource) {
         return $resource('http://:locationServerBaseUrl/api/drivers');
     });
