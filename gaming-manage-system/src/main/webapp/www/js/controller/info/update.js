/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('InfoUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.info = {};

        $scope.id = $location.search().id;
        $scope.files = [];
        $scope.ue = UE.getEditor('container');
        if ($location.search().id) {
            $http.get('info/' + $location.search().id).success(function (data) {
                $scope.info = data;
                $scope.getZoneList();
                $scope.ue.ready(function () {
                    $scope.ue.setContent($scope.info.content);
                });
            });
        } else {
            $http.get('recommend/listRecommendMapping?recommendType=news').success(function (data) {
                $scope.info.recommendMappingModelList = data;
            });

        }

        if (!$rootScope.imageServerUrl) {
            $http.get('image/server').success(function (urlResponse) {
                $rootScope.imageServerUrl = urlResponse.url;
            });
        }
        if (!$rootScope.uploadServerUrl) {
            $http.get('image/server').success(function (urlResponse) {
                $rootScope.uploadServerUrl = urlResponse.uploadUrl;
            });
        }
        $scope.uploadFile = function (files, editor, editorElem) {
            if (files && files.length > 0) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload', //$scope.uploadServerUrl,
                        file: file
                    }).progress(function (evt) {
                        $scope.message = 'uploading……';
                    }).success(function (data, status, headers, config) {
                        if (editor) {
                            editor.insertImage(editorElem, $rootScope.uploadServerUrl + data.path, '');
                        } else {
                            $scope.files.push(data);
                        }
                    });
                }
            }
        };

        $scope.uploadContent = function (files, editor) {
            $scope.uploadFile(files, editor, $scope.content);
        };

        $http.get('/enum/com.ygccw.wechat.common.info.enums.InfoZoneType').success(function (data) {
            $scope.infoZoneTypeList = data;
        });
        $scope.getZoneList = function () {
            if ($scope.info.infoZoneType.name == 'matchZone') {
                $http.get('zone/match-zone/listAll').success(function (data) {
                    $scope.zoneList = data;
                });
            } else if ($scope.info.infoZoneType.name == 'anchorZone') {
                $http.get('zone/anchor-zone/listAll').success(function (data) {
                    $scope.zoneList = data;
                });
            } else {
                $scope.zoneList = [];
            }
        }
        $http.get('/enum/com.ygccw.wechat.common.info.enums.InfoType').success(function (data) {
            $scope.infoTypeList = data;
        });

        $scope.getInfoVideoTypeList = function () {
            if ($scope.info.infoType.name == 'video') {
                $http.get('/enum/com.ygccw.wechat.common.info.enums.InfoVideoType').success(function (data) {
                    $scope.infoVideoTypeList = data;
                });
            } else {
                $scope.infoVideoTypeList = [];
            }
        }
        $scope.updateInfo = function () {
            $scope.info.content = $scope.ue.getContent();
            if ($scope.id) {
                $http.put("/info", $scope.info).success(function () {
                    alert('更新成功');
                    $state.go("info.list");
                });
            } else {
                $http.post("/info", $scope.info).success(function (data) {
                    alert('保存成功');
                    $state.go("info.list");
                });
            }
        };

        $scope.uploadTitleImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.info.titleImage = data.path;
                    });
                }
            }
        };

        $scope.uploadInfoDetailImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.infoDetail.image = data.path;
                    });
                }
            }
        };


    }]);
})(angular);