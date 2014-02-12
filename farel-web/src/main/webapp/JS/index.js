function Controller($scope, $http) {
    $http({
                    method : 'GET',
                    url : 'rest/projects'
    }).success(function(data) {
        $scope.list = data; // response data
    });

    $http({
                    method : 'GET',
                    url : 'rest/services'
    }).success(function(data) {
        $scope.servicesList = data; // response data 
    });
}