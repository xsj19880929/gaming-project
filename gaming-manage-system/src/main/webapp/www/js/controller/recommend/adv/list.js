(function (angular) {
    var module = angular.module('app');

    module.controller('AdvertisingListController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.params = {};

        var loadData = function (offset, fetchSize) {
            var url = "advertising/list?offset=" + offset + "&fetchSize=" + fetchSize;
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
                    name: '名称',
                    field: 'name'
                },
                {
                    name: '链接',
                    field: 'url'
                },
                {name: '创建时间', field: 'createTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作',
                    cellTemplate: '<button class="btn btn-primary btn-sm" ng-click="grid.appScope.updateAdvertising(row.entity.id);">编辑</button>' +
                    '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteAdvertising(row.entity.id);">删除</button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize);
                });
            }
        };


        $scope.updateAdvertising = function (id) {
            $location.search('id', id);
            $location.path('recommend/advertising/update');
        };
        $scope.deleteAdvertising = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('advertising/' + id).success(function (data) {
                    loadData($scope.offset, $scope.fetchSize);
                });
            }
        }

        $scope.search = function () {
            loadData($scope.offset, $scope.fetchSize);
        };

    }]);
})(angular);