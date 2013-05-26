 angular.module('taxi-back', ['ngResource'])
    .factory('Drivers', function($resource){
        return $resource('../drivers/:filter/:id', {}, {
            'all': {method: 'GET', isArray: true, params: {filter: 'all'}},
            'city': {method: 'GET', isArray: true, params: {filter: 'city'}}
        });
    });
