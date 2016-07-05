(function (angular) {

    var app = angular.module('app');

    app.controller('SysUserListController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        var loadData = function (offset, fetchSize) {
            var url = "/sys-user?offset=" + offset + "&fetchSize=" + fetchSize;
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
            enableColumnResizing: true,
            columnDefs: [
                {name: '序号', field: 'id', width: '5%'},
                {name: '用户名', field: 'username', width: '15%'},
                {name: '类型', field: 'userType.label', width: '8%'},
                {name: '父账户', field: 'parentUsername', width: '15%'},
                {name: '创建时间', field: 'createTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd hh:mm:ss"', width: '13%'},
                {name: '更新时间', field: 'updateTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd hh:mm:ss"', width: '13%'},
                {name: '备注', field: 'remark', width: '15%'},
                {
                    name: '操作', width: '15 %',
                    cellTemplate: '<button class="btn btn-primary btn-sm"  ng-click="grid.appScope.del(row.entity.uuid);"><i class="fa fa-close"> 删除</i> </button>'
                    + '<button class="btn btn-primary btn-sm"  ng-click="grid.appScope.resetPassword(row.entity.uuid);"><i class="fa fa-close"> 密码重置</i> </button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize);
                });
            }
        };


        $scope.resetPassword = function (id) {
            if (id)$location.search("id", id);
            $location.path('admin/sys-user/reset-password');
        };

        $scope.toAddOrUpdate = function (id) {
            if (id)$location.search("id", id);
            $location.path('admin/sys-user/update');
        }

        $scope.del = function (id) {
            $http.delete("/sys-user/" + id).success(function () {
                alert("删除成功!");
                loadData($scope.offset, $scope.fetchSize);
            }).error(function () {
                alert("删除失败!")
            });
        }

    }]);


})(angular);