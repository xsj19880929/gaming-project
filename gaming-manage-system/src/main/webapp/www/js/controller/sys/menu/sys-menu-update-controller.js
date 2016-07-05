(function (angular) {

    var app = angular.module('app');

    app.controller('SysMenuUpdateController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.sysMenu = {};
        $scope.currentMenu1 = {};
        $scope.currentMenu2 = {};

        $http.get('/sys-menu/tree').success(function (data) {
            $scope.menus1 = data;
        });

        $scope.$watch('currentMenu1', function (value) {
            if (value) {
                $scope.currentMenu1 = value;
                $scope.sysMenu.parentUuid = value.uuid;
                $scope.menus2 = value.children;
            }
        });

        $scope.updateSysMenu = function () {
            if (!$scope.currentMenu1.uuid) {
                alert("请选择菜单归属!");
                return;
            }
            $scope.sysMenu.level=2;
            if ($scope.currentMenu2&&$scope.currentMenu2.uuid) {
                $scope.sysMenu.parentUuid = $scope.currentMenu2.uuid;
                $scope.sysMenu.level=3;
            }
            $http.post('/sys-menu',$scope.sysMenu).success(function(data){
                alert("保存成功!");
                $location.path('admin/sys-menu/');
            }).error(function(data){
                alert("保存失败!");
            });

        }

    }]);


})(angular);