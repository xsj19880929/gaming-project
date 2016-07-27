/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('RecommendUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.recommend = {};

        $scope.id = $location.search().id;
        $scope.files = [];

        if ($location.search().id) {
            $http.get('recommend/' + $location.search().id).success(function (data) {
                $scope.recommend = data;
            });
        } else {
            $http.get('recommend/match-team-mapping-list').success(function (data) {
                $scope.recommend.recommendMappingModelList = data;
            });

        }
        $http.get('/enum/com.ygccw.wechat.common.recommend.enums.RecommendType').success(function (data) {
            $scope.recommendTypeList = data;
        });
        $http.get('/enum/com.ygccw.wechat.common.recommend.enums.RecommendLocal').success(function (data) {
            $scope.recommendLocalList = data;
        });
        $scope.updateRecommend = function () {
            if ($scope.id) {
                $http.put("/recommend", $scope.recommend).success(function () {
                    alert('更新成功');
                    $state.go("recommend.recommend.list");
                });
            } else {
                $http.post("/recommend", $scope.recommend).success(function (data) {
                    alert('保存成功');
                    $state.go("recommend.recommend.list");
                });
            }
        };

    }]);
})(angular);