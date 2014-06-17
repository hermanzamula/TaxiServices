angular.module("security-front", ['security-back', 'users-back'])
    .controller("profile-controller", function ($scope, Users) {

        var cookie = $.cookie(TOKEN);
        if(cookie == "null") {
            $scope.logged = false;
        } else {
            $scope.logged = !!cookie;
        }

        function removeToken() {
            $.cookie(TOKEN, "");
        }

        $scope.logout = function () {
            Users.logout({token: $.cookie(TOKEN)}, removeToken, removeToken);
        }
    });