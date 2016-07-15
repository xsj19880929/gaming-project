(function (angular) {
    var module = angular.module('app');

    module.controller('PictureListController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.params = {};

        var loadData = function (offset, fetchSize) {
            var url = "picture/list?offset=" + offset + "&fetchSize=" + fetchSize;
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
                    name: '图集描述',
                    field: 'description'
                },
                {
                    name: '访问次数',
                    field: 'visitCount'
                },
                {
                    name: '来源',
                    field: 'source'
                },
                {name: '创建时间', field: 'createTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作',
                    cellTemplate: '<button class="btn btn-primary btn-sm" ng-click="grid.appScope.updatePicture(row.entity.id);">编辑</button>' +
                    '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deletePicture(row.entity.id);">删除</button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize, _params);
                });
            }
        };


        $scope.updatePicture = function (id) {
            $location.search('id', id);
            $location.path('picture/update');
        };
        $scope.deletePicture = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('picture/' + id).success(function (data) {
                    loadData($scope.offset, $scope.fetchSize);
                });
            }
        }

        $scope.search = function () {
            loadData($scope.offset, $scope.fetchSize);
        };

    }]);
})(angular);