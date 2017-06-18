(function (angular) {
    var module = angular.module('app');

    module.controller('LinkListController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.params = {};
        //$scope.params.siteType.name = "pc";

        var loadData = function (offset, fetchSize) {
            var url = "link/list?offset=" + offset + "&fetchSize=" + fetchSize;
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
                    field: 'siteName'
                },
                {
                    name: '链接',
                    field: 'url'
                },
                {
                    name: '类型',
                    field: 'siteType.name'
                },
                {name: '创建时间', field: 'createTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作',
                    cellTemplate: '<button class="btn btn-primary btn-sm" ng-click="grid.appScope.updateLink(row.entity.id);">编辑</button>' +
                    '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteLink(row.entity.id);">删除</button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize);
                });
            }
        };


        $scope.updateLink = function (id) {
            $location.search('id', id);
            $location.path('recommend/link/update');
        };
        $scope.deleteLink = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('link/' + id).success(function (data) {
                    loadData($scope.offset, $scope.fetchSize);
                });
            }
        }

        $scope.search = function () {
            loadData($scope.offset, $scope.fetchSize);
        };
        $http.get('/enum/com.ygccw.wechat.common.link.enums.SiteType').success(function (data) {
            $scope.siteTypeList = data;
        });

        $scope.tab = function (siteType) {
            $scope.params.siteType = siteType;
            loadData($scope.offset, $scope.fetchSize);
        }

    }]);
})(angular);