(function (angular) {

    var app = angular.module('app');

    app.controller('SysUserController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.message = 'wechat-system';
        $scope.requestDate = {};

        $scope.modifyPassword = function () {
            if ($scope.requestDate.newPassword == null) {
                alert("请输入新密码!");
                return;
            }
            if ($scope.requestDate.checkPassword == null) {
                alert("请输入确认新密码!");
                return;
            }
            if ($scope.requestDate.newPassword != $scope.requestDate.checkPassword) {
                alert("两次新密码输入不一致!");
                return;
            }
            $scope.requestDate.username = $rootScope.user.username;

            $http({
                method: 'post',
                url: '/user/modify-password',
                data: $scope.requestDate,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj) {
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                }
            }).success(function (data) {
                if ("success" == data) {
                    alert("修改成功,请重新登录！！！");
                    window.location.href = "/logout";
                } else {
                    alert("修改失败！！！");
                }

            });
        }


    }]);

})(angular);