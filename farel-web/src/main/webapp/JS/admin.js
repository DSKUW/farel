function Controller($scope, $http) {

    function getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m, key, value) {
            vars[key] = value;
        });
        return vars;
    }

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
        // $rootScope.chosenProject = project;
        // Nawet zadeklarowany w rootScopie nie jest widoczny z edit.html przez
        // co nie dziala PUT
        window.location.replace("http://localhost:8080/edit.html?id=" + project.id + "&name=" + project.name);
    };

    $scope.saveProject = function(project) {
        project.id = getUrlVars()["id"];
        $http({
                        method : 'POST',
                        url : 'rest/projects',
                        data : project
        }).success(function() {
            window.location.replace("http://localhost:8080/admin.html");
        }).error(function() {
            alert("Project with this name already exists!");
        });
    };
}