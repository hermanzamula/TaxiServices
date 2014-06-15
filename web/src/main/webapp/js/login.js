angular.module("login", ['users-back', 'validators', 'drivers-back'])
    .controller("login-controller", function($scope, Users){
        $scope.login = {};
        $scope.enter = function(){
            $scope.login = {
                email: $("#user-email").val(),
                password: $("#user-password").val()
            };
            Users.login($scope.login, function(response){
                if(response.successMessage){
                    setUserToken(response.token);
                    location.href = "index.html";
                    return;
                }
                $scope.error = true;
            })
        };
    })
    .controller("registration", function($scope, Users){
        $scope.signIn = {};
        $scope.signInHelper = {};

        $scope.register = function(){
            Users.save($scope.signIn, function(response){
                if(response.errorMessage) {
                    $scope.error = true;
                    return;
                }
                setUserToken(response.token);
                location.href = "index.html";
            })
        }
    });
