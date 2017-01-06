(function (angular) {
    var module = angular.module('app');

    module.controller('InfoListController', ['$scope', '$http', '$location', '$rootScope', '$window', function ($scope, $http, $location, $rootScope, $window) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.params = {};
        $scope.params.verify = 1;
        $scope.websiteData = {};
        $http.get("info/website").success(function (data) {
            $scope.websiteData = data;
        });

        var loadData = function (offset, fetchSize) {
            var url = "info/list?offset=" + offset + "&fetchSize=" + fetchSize;
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
                    name: 'id',
                    field: 'id'
                },
                {
                    name: '标题',
                    field: 'title'
                },
                {
                    name: '资讯类型',
                    field: 'infoType',
                    cellTemplate: '<span>{{row.entity.infoType.label}}</span>'
                },
                {
                    name: '专区类型',
                    field: 'infoZoneType',
                    cellTemplate: '<span>{{row.entity.infoZoneType.label}}</span>'
                },
                {
                    name: '专区名称',
                    field: 'zoneName'
                },
                {name: '更新时间', field: 'updateTime', type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作',
                    cellTemplate: '<button class="btn btn-primary btn-sm" ng-click="grid.appScope.updateInfo(row.entity.id);">编辑</button>' +
                    '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteInfo(row.entity.id);">删除</button>' +
                    '<button class="btn btn-info btn-sm" ng-click="grid.appScope.lookInfo(row.entity.infoType.name,row.entity.id);">预览</button>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize, _params);
                });
            }
        };


        $scope.updateInfo = function (id) {
            $location.search('id', id);
            $location.path('info/update');
        };
        $scope.deleteInfo = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('info/' + id).success(function (data) {
                    loadData($scope.offset, $scope.fetchSize);
                });
            }
        }

        $scope.lookInfo = function (infoType, id) {
            var url = "";
            if (infoType == 'news') {
                url = "news/" + id + ".html";
            } else if (infoType == 'video') {
                url = "video/" + id + ".html";
            }
            $window.open($scope.websiteData.website + url);
        }

        $scope.search = function () {
            loadData($scope.offset, $scope.fetchSize);
        };
        $scope.tab = function (verify) {
            $scope.params.verify = verify;
            loadData($scope.offset, $scope.fetchSize);
        }

        $http.get('/enum/com.ygccw.wechat.common.info.enums.InfoType').success(function (data) {
            $scope.infoTypeList = data;
        });

        $scope.getInfoVideoTypeList = function () {
            if ($scope.params.infoType.name == 'video') {
                $http.get('/enum/com.ygccw.wechat.common.info.enums.InfoVideoType').success(function (data) {
                    $scope.infoVideoTypeList = data;
                });
            } else {
                $scope.infoVideoTypeList = [];
            }
        }

        $http.get('/enum/com.ygccw.wechat.common.info.enums.InfoZoneType').success(function (data) {
            $scope.infoZoneTypeList = data;
        });
        $scope.getZoneList = function () {
            if ($scope.params.infoZoneType.name == 'matchZone') {
                $http.get('zone/match-zone/listAll').success(function (data) {
                    $scope.zoneList = data;
                });
            } else if ($scope.params.infoZoneType.name == 'anchorZone') {
                $http.get('zone/anchor-zone/listAll').success(function (data) {
                    $scope.zoneList = data;
                });
            } else {
                $scope.zoneList = [];
            }
        }

    }]);
})(angular);