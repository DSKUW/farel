<!doctype html>
<html lang="en" ng-app id="ng-app">
<head>
<title>Page Title</title>
<script src="angular.js"></script>
<script>
    function PostsCtrlAjax($scope, $http) {
        $http({
                        method : 'GET',
                        url : 'rest'
        }).success(function(data) {
            $scope.posts = data; // response data 
        });
    }
</script>
</head>
<body>
    <div id="ng-app" ng-app ng-controller="PostsCtrlAjax">

        <div ng-repeat="post in posts">
            <h2>{{post.name}}</h2>
        </div>

    </div>
</body>
</html>