angular.module('request-interceptor',[])
    .config(function($httpProvider){
        function tokenInterceptor($q) {
            return {
                'request': function(config) {
                    if(!config.params) {
                        config.params = {};
                    }
                    config.params.token = $.cookie(TOKEN);
                    return config
                },
                'response': function(response) {
                    return response;
                },
                // optional method
                'requestError': function(rejection) {
                    return $q.reject(rejection);
                },
                // optional method
                'responseError': function(rejection) {
                    return $q.reject(rejection);
                }
            };
        }
        $httpProvider.interceptors.push(tokenInterceptor);
    });