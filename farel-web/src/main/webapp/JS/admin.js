function Controller($scope, $http) {
    $http({
                    method : 'GET',
                    url : 'rest/projects'
    }).success(function(data) {
        $scope.list = data; // response data
    });

    $scope.createProject = function() {
        $http({
                        method : 'POST',
                        url : 'rest/projects',
                        data : $scope.project
        });
        $http({
                        method : 'GET',
                        url : 'rest/projects'
        }).success(function(data) {
            $scope.list = data;
        });
    };

    $scope.deleteProject = function(name) {
        $http({
                        method : 'DELETE',
                        url : 'rest/projects/' + name,
        });
    };

    $scope.editProject = function(project) {
        //$rootScope.chosenProject = project;
        //Nawet zadeklarowany w rootScopie nie jest widoczny z edit.html przez co nie dziala PUT
        window.location.replace("http://localhost:8080/edit.html");
    };

    $scope.saveProject = function(project) {
        $http({
                        method : 'PUT',
                        url : 'rest/projects/' + project.name,
                        data : project
        });
        window.location.replace("http://localhost:8080/admin.html");
    };
}