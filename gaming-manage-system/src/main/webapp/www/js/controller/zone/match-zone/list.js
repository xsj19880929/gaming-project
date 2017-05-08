(function (angular) {
    var module = angular.module('app');

    module.controller('MatchZoneListController', ['$scope', '$http', '$location', '$rootScope', '$window', function ($scope, $http, $location, $rootScope, $window) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.params = {};
        $scope.websiteData = {};
        $http.get("info/msite").success(function (data) {
            $scope.websiteData = data;
        });

        var loadData = function (offset, fetchSize) {
            var url = "/zone/match-zone/list?offset=" + offset + "&fetchSize=" + fetchSize;
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
                    name: '启动赛事',
                    field: 'ifStart',
                    cellTemplate: '<span>{{row.entity.ifStart==true?"是":"否"}}</span>'
                },
                {
                    name: '主播赛事',
                    field: 'ifAnchorMatch',
                    cellTemplate: '<span>{{row.entity.ifAnchorMatch==true?"是":"否"}}</span>'
                },
                {name: '地区', field: 'matchZoneAreaName'},
                {name: '时间', field: 'matchZoneYearName'},
                {name: '状态', field: 'matchStatus.label'},
                {name: '发布时间', field: 'updateTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作',
                    cellTemplate: '<button class="btn btn-primary btn-sm" ng-click="grid.appScope.updateMatchZone(row.entity.id);">编辑</button>' +
                    '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteMatchZone(row.entity.id);">删除</button>' +
                    '<button class="btn btn-info btn-sm" ng-click="grid.appScope.lookInfo(row.entity.id);">预览</button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize);
                });
            }
        };
        $scope.lookInfo = function (id) {
            var url = "/game/" + id + "/";
            $window.open($scope.websiteData.website + url);
        }

        $scope.updateMatchZone = function (id) {
            $location.search('id', id);
            $location.path('zone/match-zone/update');
        };
        $scope.deleteMatchZone = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('zone/match-zone/' + id).success(function (data) {
                    loadData($scope.offset, $scope.fetchSize);
                });
            }
        }

        $scope.search = function () {
            loadData($scope.offset, $scope.fetchSize);
        };

    }]);
})(angular);