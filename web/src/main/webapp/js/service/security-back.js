angular.module("security-back", ['ngResource'])
    .service("Security", function ($resource) {
        return $resource("../security/:path", {}, {
            locationServerBaseUrl: {method: "GET", params: {path: "locationServerBaseUrl"}}
        })
    });