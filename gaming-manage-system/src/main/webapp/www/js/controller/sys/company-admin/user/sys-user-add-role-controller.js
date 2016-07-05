(function (angular) {

    var app = angular.module('app');

    app.controller('CompanyAdminSysUserAddRoleController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.id = $location.search().id;
        $http.get('/sys-user-role/sysUserUuid/' + $scope.id).success(function (data) {
            $scope.sysUserRole = data;
            $http.get('/sys-role/' + $scope.sysUserRole.roleUuid).success(function (data) {
                $scope.sysRole = data;
            });
        });

        $http.get('/sys-role/ByType?userType=DISTRIBUTOR').success(function (data) {
            $scope.roles = data;
        });


        if ($scope.id) {
            $http.get('sys-user/' + $scope.id).success(function (data) {
                $scope.sysUser = data;
            });
        }

        $scope.updateRoleUser = function () {
            $scope.sysUserRole.roleUuid = $scope.sysRole.uuid;
            $http.put('/sys-user-role', $scope.sysUserRole).success(function (data) {
                alert("更新成功!");
                $location.path('company-admin/sys-user/');
            }).error(function (data) {
                alert("保存失败!\n" + data.message);
            });
        }


    }]);


})(angular);