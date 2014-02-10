function ProjectController($scope, $http) {

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
        $scope.list = data;
    });

    $scope.create = function() {
        $http({
                        method : 'POST',
                        url : 'rest/projects',
                        data : $scope.project
        }).success(function() {
            location.reload();
        }).error(function() {
            alert("Project with this name already exists!");
        });;
    };

    $scope.remove = function(name) {
        $http({
                        method : 'DELETE',
                        url : 'rest/projects/' + name,
        }).success(function() {
            location.reload();
        });
    };

    $scope.edit = function(project) {
       window.location.href = "../../edit.html?id=" + project.id + "&name=" + project.name;
    };

    $scope.save = function(project) {
        project.id = getUrlVars()["id"];
        $http({
                        method : 'POST',
                        url : 'rest/projects',
                        data : project
        }).success(function() {
             window.location.href = "../../admin.html";
        }).error(function() {
            alert("Project with this name already exists!");
        });
    };
}