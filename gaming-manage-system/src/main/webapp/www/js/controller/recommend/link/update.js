/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('LinkUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.link = {};

        $scope.id = $location.search().id;
        $scope.files = [];

        if ($location.search().id) {
            $http.get('link/' + $location.search().id).success(function (data) {
                $scope.link = data;
            });
        }
        $http.get('/enum/com.ygccw.wechat.common.link.enums.SiteType').success(function (data) {
            $scope.siteTypeList = data;
        });
        $scope.updateLink = function () {
            if ($scope.id) {
                $http.put("/link", $scope.link).success(function () {
                    alert('更新成功');
                    $state.go("recommend.link.list");
                });
            } else {
                $http.post("/link", $scope.link).success(function (data) {
                    alert('保存成功');
                    $state.go("recommend.link.list");
                });
            }
        };

        $scope.uploadImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.link.image = data.path;
                    });
                }
            }
        };

    }]);
})(angular);