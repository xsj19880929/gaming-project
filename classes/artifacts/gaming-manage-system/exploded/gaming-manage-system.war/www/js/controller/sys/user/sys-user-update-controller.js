(function (angular) {

    var app = angular.module('app');

    app.controller('SysUserUpdateController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.id = $location.search().id;
        $http.get('/enum/com.ygccw.wechat.common.sys.enums.UserType').success(function (data) {
            $scope.userTypes = data;
        });

        $scope.$watch('sysUser.userType', function (value) {
            if (value) {
                $http.get('/sys-user/router/user-type?userType=' + value.name).success(function (data) {
                    $scope.parentUsers = data;
                });
            }
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

            if (!$scope.sysUser.parentUuid) {
                alert("请选择上级账号");
                return;
            }
            $scope.sysUser.parentUuid = $scope.sysUser.parentUuid.uuid;


            $http.post('sys-user', $scope.sysUser).success(function (data) {
                $location.path('admin/sys-user/');
            }).error(function (data) {
                alert("保存失败!\n"+data.message);
            });
        }

        $scope.resetPassword=function(){
            $http.put('/sys-user/reset-password', $scope.sysUser).success(function (data) {
                $location.path('admin/sys-user/');
            }).error(function (data) {
                alert("保存失败!\n"+data.message);
            });
        }

    }]);


})(angular);