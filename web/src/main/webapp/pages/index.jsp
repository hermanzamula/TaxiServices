<html ng-app="main">
<body ng-controller="main">
<script type="text/javascript" src="../lib/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript"  src ="../lib/angularjs/angular.min.js"></script>
<script type="text/javascript" src="../lib/angularjs/angular-resource.min.js"></script>
<script type="text/javascript" src="../js/main.js"></script>
<script>

    var request = {};
request.name = "herman";
request.lastName = "zamula";
request.email = "herman@com";
request.password = "pwd";

    function post() {
        $.ajax({
            type: "POST", url: "../user/add/",
            data: request, success: function(response){
                console.log(response);
            }, dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        });
    }

    function get() {
        $.ajax({
            type: "GET", url: "../user/",
            data: null, success: function(response){
                console.log(response);
            }, dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        });
    }
</script>
<h2>Hello word</h2>

<button onclick="get()">get</button>
<button ng-click="click()">post</button>
</body>
</html>
