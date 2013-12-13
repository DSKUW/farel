function ProjectController($scope, $http) {
    $scope.createProject = function() {
        $http({
                        method : 'POST',
                        url : 'rest/projects/' + $scope.project.name,
                        data : $scope.project
        });
    };
}