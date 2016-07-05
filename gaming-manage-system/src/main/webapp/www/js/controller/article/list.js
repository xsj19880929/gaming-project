/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('ArticleListController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {
        $scope.offset = 0;
        $scope.fetchSize = 25;
        $scope.articleTypes = {};
        $scope.article = {};
        var _params = {"status": 1};

        $http.get('/article-type-model/listAll').success(function (data) {
            $scope.articleTypes = data;
        });

        var loadData = function (offset, fetchSize, _params) {
            var url = "/article/list?offset=" + offset + "&fetchSize=" + fetchSize;
            $http.post(url, _params).success(function (data) {
                $scope.gridOptions.data = data.list;
                $scope.gridOptions.totalItems = data.total;
                $scope.offset = offset;
                $scope.fetchSize = fetchSize;
            });
        };

        $scope.gridOptions = {
            data: loadData($scope.offset, $scope.fetchSize, _params),
            paginationPageSizes: [$scope.fetchSize, $scope.fetchSize * 2, $scope.fetchSize * 3],
            paginationPageSize: $scope.fetchSize,
            useExternalPagination: true,
            columnDefs: [
                {
                    name: '标题',
                    field: 'title',
                    cellTemplate: '<a href="javascript:void(0)" ng-click="grid.appScope.LookArticle(row.entity.id);" title="{{row.entity.title}}">{{row.entity.title}}</a>',
                    width: 360
                },
                {name: '是否发布', width: 90, cellTemplate: '<span ng-if="row.entity.published">是</span><span ng-if="!row.entity.published">否</span>'},
                {name: '文章类型', field: 'typeName', width: 90},
                {name: '浏览数', field: 'pv', width: 90},
                {name: '发布时间', field: 'updateTime', width: 160, type: 'date', cellFilter: 'date:"yyyy-MM-dd HH:mm:ss"'},
                {
                    name: '操作', width: 170,
                    cellTemplate: '<span class="display-lineblock padding-leftRight-5 cz-btn" ng-click="grid.appScope.updateArticle(row.entity.id);">编辑</span>|' +
                    '<span class="display-lineblock padding-leftRight-5 cz-btn" ng-click="grid.appScope.deleteArticle(row.entity.id);">删除</span>|' +
                    '<span class="display-lineblock padding-leftRight-5 cz-btn" ng-click="grid.appScope.LookArticle(row.entity.id);">预览</span>'
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (page, fetchSize) {
                    loadData((page - 1) * fetchSize, fetchSize, _params);
                });
            }
        };
        $scope.publishes = [{"published": true, "publishedLabel": "是"}, {"published": false, "publishedLabel": "否"}];

        $scope.listByArticleType = function (uuid) {
            if (uuid == 0) {
                _params = {"status": 1};
            } else {
                _params.typeUuid = uuid;
            }
            loadData($scope.offset, $scope.fetchSize, _params);
        };

        $scope.updateArticle = function (id) {
            $location.search('articleId', id);
            $location.path('wechat/article/update');
        };
        $scope.deleteArticle = function (id) {
            if (confirm("确认删除？")) {
                $http.delete('article/' + id).success(function (data) {
                    $http.get('/article-type-model/listAll').success(function (data) {
                        $scope.articleTypes = data;
                    });
                    loadData($scope.offset, $scope.fetchSize, _params);
                });
            }
        }

        $scope.viewArticle = function (id) {
            $location.search('articleId', id);
            $location.path('wechat/article/update');
        };

        $scope.LookArticle = function (id) {
            window.open($rootScope.mobilePath + "article-detail/" + id);
        }
    }]);
})(angular);