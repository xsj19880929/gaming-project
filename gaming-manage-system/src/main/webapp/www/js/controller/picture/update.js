/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('PictureUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.picture = {};

        $scope.id = $location.search().id;
        $scope.files = [];
        $scope.pictureDetail = {};
        $scope.pictureDetailList = [];
        $scope.pictureDetail.sort = 1;
        if ($location.search().id) {
            $http.get('picture/' + $location.search().id).success(function (data) {
                $scope.picture = data;
                $scope.pictureDetailList = $scope.picture.pictureDetailList;
                $scope.pictureDetail.sort = $scope.pictureDetailList.length + 1;
            });
        } else {
            $http.get('recommend/listRecommendMapping?recommendType=picture').success(function (data) {
                $scope.picture.recommendMappingModelList = data;
            });

        }
        $scope.updatePicture = function () {
            if ($scope.id) {
                $http.put("/picture", $scope.picture).success(function () {
                    alert('更新成功');
                    $state.go("picture.list");
                });
            } else {
                $http.post("/picture", $scope.picture).success(function (data) {
                    alert('保存成功');
                    $state.go("picture.list");
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
                        $scope.picture.image = data.path;
                    });
                }
            }
        };

        $scope.uploadPictureDetailImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.pictureDetail.image = data.path;
                    });
                }
            }
        };
        $scope.savePictureDetail = function () {
            if ($scope.pictureDetail) {
                $scope.pictureDetailList.push(angular.copy($scope.pictureDetail));
                $scope.picture.pictureDetailList = $scope.pictureDetailList;
                $scope.pictureDetail = {};
                $scope.pictureDetail.sort = $scope.pictureDetailList.length + 1;
            }
        };
        $scope.deletePictureDetail = function (pictureDetail) {
            for (var i = 0; i < $scope.pictureDetailList.length; i++) {
                if ($scope.pictureDetailList[i] == pictureDetail) {
                    $scope.pictureDetailList.splice(i, 1);
                }
            }
            $scope.picture.pictureDetailList = $scope.pictureDetailList;
            $scope.pictureDetail.sort = $scope.pictureDetailList.length + 1;

        }


    }]);
})(angular);