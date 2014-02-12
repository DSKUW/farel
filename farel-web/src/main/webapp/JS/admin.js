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
            alert("Forbidden!");
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

    $scope.save = function() {
        if (typeof $scope.finalProject == 'undefined') {
            window.location.href = "../../admin_manage.html";
        }
        $scope.finalProject.id = getUrlVars()["id"];
        $scope.finalProject.name = document.getElementById('nameField').value;
        $http({
                        method : 'POST',
                        url : 'rest/projects/update',
                        data : $scope.finalProject
        }).success(function() {
            window.location.href = "../../admin_manage.html";
        }).error(function() {
            alert("Forbidden!");
        });
    };

    $(window).load(function() {
        if (location.pathname == "/edit.html")
            document.getElementById("nameField").value = getUrlVars()["name"];
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
        $http({
                        method : 'POST',
                        url : 'rest/services',
                        data : $scope.service
        }).success(function() {
            location.reload();
        }).error(function() {
            alert("Forbidden!");
        });
        ;
    };

    var serviceTempElement;

    $scope.remove = function(element) {
        serviceTempElement = element;
    };

    $scope.continueRemoval = function() {
        $http({
                        method : 'DELETE',
                        url : 'rest/services/' + serviceTempElement.name,
        }).success(function() {
            location.reload();
        });
    };

    $scope.edit = function(service) {
        window.location.href = "../../edit_services.html?id=" + service.id + "&name=" + service.name + "&serviceType="
                        + service.serviceType + "&address=" + service.address + "&restEndPoint=" + service.restEndPoint;
    };

    $scope.save = function() {
        if (typeof $scope.finalSrvice == 'undefined') {
            window.location.href = "../../admin_manage_services.html";
        }
        $scope.finalSrvice.id = getUrlVars()["id"];
        $scope.finalSrvice.name = document.getElementById('nameField').value;
        $scope.finalSrvice.address = document.getElementById('address').value;
        $scope.finalSrvice.restEndPoint = document.getElementById('endPoint').value;
        var e = document.getElementById("dropDownList");
        var chosen = e.options[e.selectedIndex].value;
        $scope.finalSrvice.serviceType = ServiceType[chosen];
        $http({
                        method : 'POST',
                        url : 'rest/services/update',
                        data : $scope.finalSrvice
        }).success(function() {
            window.location.href = "../../admin_manage_services.html";
        }).error(function() {
            alert("Forbidden!");
        });
    };

    $(window).load(function() {
        if (location.pathname == "/edit_services.html") {
            document.getElementById("nameField").value = getUrlVars()["name"];
            document.getElementById("address").value = getUrlVars()["address"];
            document.getElementById("endPoint").value = getUrlVars()["restEndPoint"];
            if (getUrlVars()["serviceType"] == "BUG_TRACKING") {
                document.getElementById("dropDownList").selectedIndex = 0;
            }
            if (getUrlVars()["serviceType"] == "CODE_REVIEW") {
                document.getElementById("dropDownList").selectedIndex = 1;
            }
            if (getUrlVars()["serviceType"] == "CONTINUOUS_INTEGRATION") {
                document.getElementById("dropDownList").selectedIndex = 2;
            }
            if (getUrlVars()["serviceType"] == "SYSTEMS_MONITORING") {
                document.getElementById("dropDownList").selectedIndex = 3;
            }
        }
    });
}