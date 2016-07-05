(function (angular) {

    var app = angular.module('app');

    app.controller('SysRoleListController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        var loadData = function (offset, fetchSize) {
            var url = "/sys-role?offset=" + offset + "&fetchSize=" + fetchSize;
            $http.get(url).success(function (data) {
                $scope.gridOptions.data = data.list;
                $scope.gridOptions.totalItems = data.total;

                $scope.offset = offset;
                $scope.fetchSize = fetchSize;
            });
        };


        $scope.gridOptions = {
            data: loadData($scope.offset, $scope.fetchSize),
            paginationPageSizes: [$scope.fetchSize, $scope.fetchSize * 2, $scope.fetchSize * 3],
            paginationPageSize: $scope.fetchSize,
            useExternalPagination: true,
            columnDefs: [
                {name: '序号', field: 'id'},
                {name: '角色名', field: 'name'},
                {name: '类型', field: 'userType.label'},
                {name: '描述', field: 'remark'},
                {name: '创建时间', field: 'createTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd hh:mm:ss"'},
                {name: '更新时间', field: 'updateTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd hh:mm:ss"'},
                {
                    name: '操作', width: '30%',
                    cellTemplate: '<button class="btn btn-primary btn-sm"  ng-click="grid.appScope.del(row.entity.uuid);"><i class="fa fa-close"> 删除</i> </button>'
                    + '<button class="btn btn-primary btn-sm"  ng-click="grid.appScope.toAddOrUpdate(row.entity.uuid);"><i class="fa fa-close"> 修改</i> </button>'
                    + '<button class="btn btn-primary btn-sm"  ng-click="grid.appScope.toAddSysUser(row.entity.uuid);"><i class="fa fa-close">添加用户</i> </button>'
                    + '<button class="btn btn-primary btn-sm"  ng-click="grid.appScope.toAddSysMenu(row.entity.uuid);"><i class="fa fa-close">添加权限</i> </button>'

                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize);
                });
            }
        };


        $scope.toAddOrUpdate = function (id) {
            if (id)$location.search("id", id);
            $location.path('/admin/sys-role/update');
        }

        $scope.del = function (id) {
            $http.delete("/sys-role/" + id).success(function () {
                alert("删除成功!");
                loadData($scope.offset, $scope.fetchSize);
            }).error(function () {
                alert("删除失败!")
            });
        }

        $scope.toAddSysUser = function (id) {
            $location.search("uuid", id);
            $location.path('/admin/sys-role/role-add-user');
        }


        $scope.toAddSysMenu = function (id) {
            $location.search("uuid", id);
            $location.path('/admin/sys-role/role-add-menu');
        }

    }]);


})(angular);