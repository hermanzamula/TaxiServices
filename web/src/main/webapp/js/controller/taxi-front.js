angular.module('taxi-front', ['taxi-back', 'users-back'])
    .controller('taxi-city-list', function ($scope, $routeParams, Drivers, Cities) {
        $scope.city = $routeParams.city ? $routeParams.city : 1;
        Drivers.city({id: $routeParams.city}, function (drivers) {
            $scope.drivers = drivers;
        });
        Cities.byCountry({id: 1}, function (cities) {
            $scope.cities = cities;
        })
    })
    .controller('taxi-details', function ($scope, $routeParams, Drivers, Countries, Cities, Users) {

        $scope.city = $routeParams.city ? $routeParams.city : 1;
        $scope.message = "";

        Drivers.details({id: $routeParams.id}, function (details) {
            $scope.details = details;
            $scope.driversByCity = Drivers.short({id: $scope.details.cities[0]});
        });

        function updateComments() {
            Drivers.comments({id: $routeParams.id}, function (comments) {
                $scope.comments = comments;
            });
        }
        updateComments();

        function comment(cookie) {
            var newVar = {};
            newVar.driver = $scope.details.id;
            newVar.message = $scope.message;
            newVar.token = cookie;

            Drivers.comment(newVar, function () {
                $scope.message = "";
                updateComments();
            });
        }

        $scope.sendMessage = function () {
            var cookie = $.cookie(TOKEN);
            if (cookie && cookie != 'null') {
                comment(cookie);
                return;
            }
            showModal();
        };
        setupConfirmation($scope, Countries, Cities, Users, comment);
    });

function setupConfirmation($scope, Countries, Cities, Users, comment) {

    $scope.login = {};
    $scope.signIn = {};
    $scope.signInHelper = {};
    $scope.countries = Countries.query();

    $scope.$watch("signIn.country", function(val){
        if(!val) return;
        $scope.cities = Cities.byCountry({id: val});
    });

    function checkResponse(response) {
        if (response.successMessage) {
            $.cookie("yoursTaxiTokenId", response.token);
            comment(response.token);
            hideModal();
        }
    }

    $scope.confirm = function () {
        if (!$scope.registration) {
            console.log($scope.login);
            Users.login($scope.login, function (response) {
                console.log("Login response: ");
                if (response.successMessage) {
                    $.cookie("yoursTaxiTokenId", response.token);
                    comment(response.token);
                    hideModal();
                }
            });
            return;
        }
        Users.save($scope.signIn, function(response){
            console.log(response);
            checkResponse(response);
        })
    };

}

function showModal() {
    $(".modal").modal("show");
}

function hideModal() {
    $(".modal").modal("hide");
}

