(function (angular) {

    var app = angular.module('app');

    app.controller('CompanyAdminSysUserUpdateController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.id = $location.search().id;
        $scope.userTypes = [{name: "DISTRIBUTOR", label: "经销商"}];

        $http.get('/sys-role/ByType?userType=DISTRIBUTOR').success(function (data) {
            $scope.roles = data;
        });
        if ($scope.id) {
            $http.get('sys-user/' + $scope.id).success(function (data) {
                $scope.sysUser = data;
            });
        }

        $scope.updateSysUser = function () {
            if (!$scope.sysUser.userType) {
                alert("请选择账户类型");
                return;
            }
            ;
            if (!$scope.sysUser.username) {
                alert("请输入用户名");
                return;

            }
            if (!$scope.sysUser.password) {
                alert("请输入密码");
                return;

            }


            $scope.sysUser.parentUuid = $rootScope.user.uuid;


            $http.post('sys-user', $scope.sysUser).success(function (data) {
                $location.path('company-admin/sys-user/');
            }).error(function (data) {
                alert("保存失败!\n" + data.message);
            });

        }



        $scope.resetPassword = function () {
            $http.put('/sys-user/reset-password', $scope.sysUser).success(function (data) {
                $location.path('company-admin/sys-user/');
            }).error(function (data) {
                alert("保存失败!\n" + data.message);
            });
        }

    }]);


})(angular);