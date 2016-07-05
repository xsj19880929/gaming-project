(function (angular) {

    var app = angular.module('app');

    app.controller('SysRoleAddMenuController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {

        $scope.uuid = $location.search().uuid;
        $http.get('sys-role/' + $scope.uuid).success(function (data) {
            $scope.sysRole = data;
        });

        $http.get('/sys-menu/tree').success(function (data) {
            $scope.sysMenus = data;
            $http.get('/sys-role-menu/findByRoleUuid/' + $scope.uuid).success(function (data) {
                $scope.currentUserMenu = data;
                recursionSelected($scope.sysMenus);
            });
        });


        $scope.updateMenuToRole = function () {
            var menuRoles = [];
            angular.forEach($scope.sysMenus, function (value) {
                recursionForUpdate(menuRoles, value);
            });
            $http.post('/sys-role-menu/batch/' + $scope.uuid, menuRoles).success(function () {
                alert("操作成功!");
                $location.path("admin/sys-role/");
            }).error(function () {
                alert("操作失败!");
            });
        }

        $scope.chk = function (menu, menu1, menu2) {
            if (menu.level < 3) {
                recursion(menu.children, menu.selected);
            }
            if (menu2)parentSelected(menu2);
            if (menu1)parentSelected(menu1);
        }


        function recursionSelected(menus) {
            for (var i = 0; i < menus.length; i++) {

                for (var j = 0; j < $scope.currentUserMenu.length; j++) {
                    if ($scope.currentUserMenu[j].menuUuid == menus[i].uuid) {
                        menus[i].selected = true;
                        break;
                    }
                }

                recursionSelected(menus[i].children)
            }
        }

        function recursionForUpdate(menuRoles, menu) {
            if (menu.selected) {
                var menuRole = {};
                menuRole.roleUuid = $scope.uuid;
                menuRole.menuUuid = menu.uuid;
                menuRoles.push(menuRole);
            }
            var children = menu.children;
            if (!children || null == children || children.length < 1)return;
            for (var i = 0; i < children.length; i++) {
                recursionForUpdate(menuRoles, children[i]);
            }

        }


        function parentSelected(menu) {
            var flag = false;
            angular.forEach(menu.children, function (value) {
                if (value.selected) {
                    flag = true;
                    return;
                }
            })

            menu.selected = flag;
        }

        function recursion(children, selected) {
            if (!children || null == children || children.length < 1) return;
            for (var i = 0; i < children.length; i++) {
                children[i].selected = selected;
                recursion(children[i].children, selected)
            }
        }


    }

    ])
    ;


})(angular);