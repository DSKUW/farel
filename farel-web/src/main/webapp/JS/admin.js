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
        });
        ;
    };

    var tempElement;

    $scope.remove = function(element) {
        tempElement = element;
    };

    $scope.continueRemoval = function() {
        $http({
                        method : 'DELETE',
                        url : 'rest/projects/' + tempElement.name,
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
            window.location.href = "../../admin_manage.html";
        }).error(function() {
            alert("Project with this name already exists!");
        });
    };

    $(window).load(function() {
        if(location.pathname=="/edit.html") document.getElementById("nameField").value = getUrlVars()["name"];
    });
}

function ServiceController($scope, $http) {

    function getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m, key, value) {
            vars[key] = value;
        });
        return vars;
    }

    $http({
                    method : 'GET',
                    url : 'rest/services'
    }).success(function(data) {
        $scope.serviceList = data;
    });

    var ServiceType = [ "BUG_TRACKING", "CODE_REVIEW", "CONTINUOUS_INTEGRATION", "SYSTEMS_MONITORING" ];
    $scope.create = function() {
        var e = document.getElementById("dropDownList");
        var chosen = e.options[e.selectedIndex].value;
        $scope.service.serviceType = ServiceType[chosen];
        alert(JSON.stringify($scope.service));
        $http({
                        method : 'POST',
                        url : 'rest/services',
                        data : $scope.service
        }).success(function() {
            location.reload();
        }).error(function() {
            alert("Project with this name already exists!");
        });
        ;
    };

    var serviceTempElement;

    $scope.remove = function(element) {
        tempElement = element;
    };

    $scope.continueRemoval = function() {
        $http({
                        method : 'DELETE',
                        url : 'rest/projects/' + serviceTempElement.name,
        }).success(function() {
            location.reload();
        });
    };

    $scope.edit = function(service) {
        window.location.href = "../../edit_services.html?id=" + service.id + "&name=" + service.name + "&serviceType="
                        + service.serviceType + "&address=" + service.address + "&restEndPoint=" + service.restEndPoint;
    };

    $scope.save = function(service) {
        var e = document.getElementById("dropDownList");
        var chosen = e.options[e.selectedIndex].value;
        service.serviceType = ServiceType[chosen];
        service.id = getUrlVars()["id"];
        $http({
                        method : 'POST',
                        url : 'rest/services',
                        data : service
        }).success(function() {
            window.location.href = "../../admin_manage_services.html";
        }).error(function() {
            alert("Project with this name already exists!");
        });
    };
    
    $(window).load(function() {
        if(location.pathname=="/edit_services.html") {
            document.getElementById("nameField").value = getUrlVars()["name"];
            document.getElementById("address").value = getUrlVars()["address"];
            document.getElementById("endPoint").value = getUrlVars()["restEndPoint"];
            if(getUrlVars()["serviceType"] == "BUG_TRACKING") {
                document.getElementById("dropDownList").selectedIndex = 1;
            }
            if(getUrlVars()["serviceType"] == "CODE_REVIEW") {
                document.getElementById("dropDownList").selectedIndex = 2;
            }
            if(getUrlVars()["serviceType"] == "CONTINUOUS_INTEGRATION") {
                document.getElementById("dropDownList").selectedIndex = 3;
            }
            if(getUrlVars()["serviceType"] == "SYSTEMS_MONITORING") {
                document.getElementById("dropDownList").selectedIndex = 4;
            }
        }
    });
}