(function (angular) {

    var app = angular.module('app');

    app.controller('SysRoleAddUserController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {

        $scope.uuid = $location.search().uuid;
        $http.get('sys-role/' + $scope.uuid).success(function (data) {
            $scope.sysRole = data;
        });


        $scope.offset = 0;
        $scope.fetchSize = 1000000;
        var loadData = function (offset, fetchSize) {
            var url = "/sys-user?roleUuid=" + $scope.uuid + "&offset=" + offset + "&fetchSize=" + fetchSize;
            $http.get(url).success(function (data) {
                $scope.gridOptions.data = data.list;
                $scope.gridOptions.totalItems = data.total;

                $scope.offset = offset;
                $scope.fetchSize = fetchSize;

                $http.get(' /sys-user/by-role-uuid/' + $scope.uuid).success(function (data) {
                    $scope.currentSysUsers = data;

                    for (var i = 0; i < $scope.currentSysUsers.length; i++) {
                        for (var j = 0; j < $scope.gridOptions.data.length; j++) {
                            if ($scope.currentSysUsers[i].uuid == $scope.gridOptions.data[j].uuid) {
                                $scope.gridOptions.data[j].selected = true;
                                break;
                            }
                        }
                    }
                });
            });
        };


        $scope.gridOptions = {
            data: loadData($scope.offset, $scope.fetchSize),
            paginationPageSizes: [$scope.fetchSize, $scope.fetchSize * 2, $scope.fetchSize * 3],
            paginationPageSize: $scope.fetchSize,
            useExternalPagination: true,
            columnDefs: [
                {name: '选择', cellTemplate: '<input type="checkbox"  ng-model="row.entity.selected" />'},
                {name: '序号', field: 'id'},
                {name: '用户名', field: 'username'},
                {name: '描述', field: 'remark'},
                {name: '父账户', field: 'parentUsername'},

                {name: '创建时间', field: 'createTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd hh:mm:ss"'},
                {name: '更新时间', field: 'updateTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd hh:mm:ss"'}

            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize);
                });
            }
        };


        $scope.updateUserToRole = function () {
            var data = [];
            for (var j = 0; j < $scope.gridOptions.data.length; j++) {
                if ($scope.gridOptions.data[j].selected) {
                    var item = {};
                    item.userUuid = $scope.gridOptions.data[j].uuid;
                    item.roleUuid = $scope.sysRole.uuid;
                    data.push(item);
                }
            }
            $http.post('/sys-user-role/batch/' + $scope.uuid, data).success(function () {
                alert("操作成功!");
                $location.path("admin/sys-role/");
            }).error(function () {
                alert("操作失败!");
            });
        }
    }]);


})(angular);