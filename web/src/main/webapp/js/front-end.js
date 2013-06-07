angular.module('front-end', [])
    .directive('ellipsize', function () {
        return function (scope, element, attr) {
            var elementHeight = scope.$eval(attr.ellipsize);
            if(!elementHeight && attr.ellipsize !== "auto") elementHeight = $(element).height();
            $(element).dotdotdot({
                 wrap: "letter",
                 watch: true,
                 height: elementHeight
            });
            setTimeout(function () {
                 $(element).trigger("update");
            }, 0);
        }
    })
    .filter('truncate', function () {
        return function (input, symbols) {
            if (input && input.length > symbols) {
                return input.substring(0, symbols) + "...";
            }
            return input;
        }
    });