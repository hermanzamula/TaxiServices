 angular.module('taxi-back', ['ngResource'])
    .factory('Drivers', function($resource){
        return $resource('../drivers/:filter/:id/:path', {}, {
            'all': {method: 'GET', isArray: true, params: {filter: 'all'}},
            'city': {method: 'GET', isArray: true, params: {filter: 'city'}},
            'short': {method: 'GET', isArray: true, params: {path: 'short', filter: 'city' }},
            'details': {method: 'GET', params: {filter: 'details'}},
            'comments': {method: 'GET', isArray: true, params: {filter: 'comments'}},
            'comment': {method: "POST", params: {filter: 'comment'}}
        });
    })
     .factory('Cities', function($resource){
         return $resource('../cities/:path/:id', {}, {
             'byCountry': {method: "GET", isArray: true, params:{path: 'country'}}
         })
     })
     .factory('Countries', function($resource){
         return $resource('../countries');
     })
     .factory("Comments", function($resource){
         return $resource('../comments/top/:value');
     })
     .factory('DriversLocation', function($resource) {
         return $resource('http://:locationServerBaseUrl/api/drivers');
     }) ;
