function ProjectController($scope, $http) {
    $scope.createProject = function() {
        $http({
                        method : 'POST',
                        url : 'rest/projects',
                        data : [$scope.project]
        });
    };
}