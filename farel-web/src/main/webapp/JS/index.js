function Controller($scope, $http) {
    $http({
                    method : 'GET',
                    url : 'rest/projects'
    }).success(function(data) {
        $scope.list = data; // response data 
    });
}