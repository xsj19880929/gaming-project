/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('HotKeywordsUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.hotKeywords = {};

        $scope.id = $location.search().id;

        if ($location.search().id) {
            $http.get('hotKeywords/' + $location.search().id).success(function (data) {
                $scope.hotKeywords = data;
            });
        }
        $scope.updateHotKeywords = function () {
            if ($scope.id) {
                $http.put("/hotKeywords", $scope.hotKeywords).success(function () {
                    alert('更新成功');
                    $state.go("recommend.hot-keywords.list");
                });
            } else {
                $http.post("/hotKeywords", $scope.hotKeywords).success(function (data) {
                    alert('保存成功');
                    $state.go("recommend.hot-keywords.list");
                });
            }
        };


    }]);
})(angular);