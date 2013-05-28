 angular.module('taxi-back', ['ngResource'])
    .factory('Drivers', function($resource){
        return $resource('../drivers/:filter/:id/:path', {}, {
            'all': {method: 'GET', isArray: true, params: {filter: 'all'}},
            'city': {method: 'GET', isArray: true, params: {filter: 'city'}},
            'short': {method: 'GET', isArray: true, params: {path: 'short', filter: 'city' }},
            'details': {method: 'GET', params: {filter: 'details'}}
        });
    });
