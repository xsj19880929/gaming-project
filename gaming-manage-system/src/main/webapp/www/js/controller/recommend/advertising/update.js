/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('AdvertisingUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.advertising = {};

        $scope.id = $location.search().id;
        $scope.files = [];

        if ($location.search().id) {
            $http.get('advertising/' + $location.search().id).success(function (data) {
                $scope.advertising = data;
            });
        } else {
            $http.get('advertising/match-team-mapping-list').success(function (data) {
                $scope.advertising.advertisingMappingModelList = data;
            });

        }
        $http.get('/enum/com.ygccw.wechat.common.advertising.enums.AdvertisingType').success(function (data) {
            $scope.advertisingTypeList = data;
        });
        $scope.updateAdvertising = function () {
            if ($scope.id) {
                $http.put("/advertising", $scope.advertising).success(function () {
                    alert('更新成功');
                    $state.go("recommend.advertising.list");
                });
            } else {
                $http.post("/advertising", $scope.advertising).success(function (data) {
                    alert('保存成功');
                    $state.go("recommend.advertising.list");
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
                        $scope.advertising.image = data.path;
                    });
                }
            }
        };

    }]);
})(angular);