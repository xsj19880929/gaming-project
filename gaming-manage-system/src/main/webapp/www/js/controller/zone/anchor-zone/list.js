(function (angular) {
    var module = angular.module('app');

    module.controller('AnchorZoneListController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.params = {};

        var loadData = function (offset, fetchSize) {
            var url = "/zone/anchor-zone/list?offset=" + offset + "&fetchSize=" + fetchSize;
            $http.post(url, $scope.params).success(function (data) {
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
                {
                    name: '专区名称',
                    field: 'name'
                },
                {
                    name: '访问次数',
                    field: 'visitCount'
                },
                {
                    name: '姓名',
                    field: 'username'
                },
                {
                    name: '别名',
                    field: 'otherUsername'
                },
                {name: '平台', field: 'platformName'},
                {name: '发布时间', field: 'updateTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作',
                    cellTemplate: '<button class="btn btn-primary btn-sm" ng-click="grid.appScope.updateAnchorZone(row.entity.id);">编辑</button>' +
                    '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteAnchorZone(row.entity.id);">删除</button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize, _params);
                });
            }
        };


        $scope.updateAnchorZone = function (id) {
            $location.search('id', id);
            $location.path('zone/anchor-zone/update');
        };
        $scope.deleteAnchorZone = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('zone/anchor-zone/' + id).success(function (data) {
                    loadData($scope.offset, $scope.fetchSize);
                });
            }
        }

        $scope.search = function () {
            loadData($scope.offset, $scope.fetchSize);
        };

    }]);
})(angular);