angular.module('validators', [])
    .directive('differentThen', function() {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                scope.isDifferent = true;
                scope.$watch(attrs.ngModel, function (viewValue) {
                     if(!viewValue) return;
                     return check(viewValue, attrs.differentThen)
                });

                scope.$watch(attrs.differentThen, function(viewValue) {
                     if(!viewValue) return;
                     return check(viewValue, attrs.ngModel);
                });

                function check(viewValue, model) {
                    if (viewValue === scope.$eval(model)) {
                        ctrl.$setValidity('differentThen', false);
                        scope.isDifferent = false;
                    } else {
                        ctrl.$setValidity('differentThen', true);
                        scope.isDifferent = true;
                    }
                }
            }
    }})
    .directive('likeAs', function() {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                scope.$watch(attrs.ngModel, function (viewValue) {
                    check(viewValue, attrs.likeAs)
                });

                scope.$watch(attrs.likeAs, function(viewValue) {
                    check(viewValue, attrs.ngModel);
                });

                function check(viewValue, model) {
                    if (viewValue === scope.$eval(model)) {
                        ctrl.$setValidity('likeAs', true);
                    } else {
                        ctrl.$setValidity('likeAs', false);
                    }
                }
            }
    }})
    .directive('inputValidator', function() {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                var changed = false;
                function setValidity(val) {
                    if(!val) {
                        ctrl.$setValidity('inputValidator', false);
                    } else {
                        ctrl.$setValidity('inputValidator', true);
                    }
                    setTimeout(function() {
                        scope.$apply()
                    });
                }

                elm.on('blur', function() {
                    changed = true;
                    setValidity(ctrl.$viewValue);
                });
                scope.$watch(attrs.ngModel, function(newValue) {
                    if(changed) setValidity(newValue);
                    if(newValue) changed = true;
                });
                if(attrs.inputValidator) {
                    scope.$watch(attrs.inputValidator, function(val) {
                        changed = true;
                        ctrl.$setValidity('inputValidator', val?true:false);
                    });
                }
                ctrl.$setValidity('inputValidator', attrs.inputValidator? scope.$eval(attrs.inputValidator):true);

            }
        }
    });