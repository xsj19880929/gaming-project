(function (angular) {

    var app = angular.module('app');

    app.controller('SysRoleUpdateController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.id = $location.search().id;
        $http.get('/enum/com.ygccw.wechat.common.sys.enums.UserType').success(function (data) {
            $scope.userTypes = data;
        });
        if ($scope.id) {
            $http.get('sys-role/' + $scope.id).success(function (data) {
                $scope.sysRole = data;
            });
        }

        $scope.updateSysRole = function () {
            if(!$scope.sysRole){
                alert("请输入");
                return;
            }
            if(!$scope.sysRole.name){
                alert("请输入角色名");
                return;
            }
            if(!$scope.sysRole.userType){
                alert("请选择角色类型");
                return;
            }
            if ($scope.id) {
                $http.put('sys-role', $scope.sysRole).success(function (data) {
                    $location.path('admin/sys-role/');
                }).error(function (data) {
                    alert("保存失败!");
                });
            } else {
                $http.post('sys-role', $scope.sysRole).success(function (data) {
                    $location.path('admin/sys-role/');
                }).error(function (data) {
                    alert("保存失败!");
                });
            }

        }

    }]);


})(angular);