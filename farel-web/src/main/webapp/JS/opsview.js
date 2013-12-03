function PostsCtrlAjax($scope, $http) {
    $http({
                    method : 'GET',
                    url : 'rest/opsview/jenkins'
    }).success(function(data) {
        $scope.list = data; // response data 
    });
}