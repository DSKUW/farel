function AddController($scope, $http) {
    $scope.createProject = function() {
        $http({
                        method : 'POST',
                        url : 'rest/projects',
                        data : $scope.project
        });
    };
}

function DeleteController($scope, $http) {
    $http({
                    method : 'GET',
                    url : 'rest/projects'
    }).success(function(data) {
        $scope.list = data; // response data 
    });

    $scope.deleteProject = function(name) {
        $http({
                        method : 'DELETE',
                        url : 'rest/projects/' + name,
        });
    };
}