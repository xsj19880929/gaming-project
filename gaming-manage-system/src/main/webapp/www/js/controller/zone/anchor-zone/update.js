/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('AnchorZoneUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.anchorZone = {};

        $scope.id = $location.search().id;
        $scope.files = [];
        $scope.anchorZonePlatformList = [];
        $scope.anchorZoneHonor = {};
        $scope.anchorZoneHonorList = [];

        $http.get('zone/anchor-zone/anchor-zone-platform/list').success(function (data) {
            $scope.anchorZonePlatformList = data;
        });

        if ($location.search().id) {
            $http.get('zone/anchor-zone/' + $location.search().id).success(function (data) {
                $scope.anchorZone = data;
                $scope.anchorZoneHonorList = $scope.anchorZone.anchorZoneHonorList;
            });
        } else {
            $http.get('recommend/listRecommendMapping?recommendType=anchorZone').success(function (data) {
                $scope.anchorZone.recommendMappingModelList = data;
            });
            $http.get('zone/anchor-zone/match-zone/list').success(function (data) {
                $scope.anchorZone.anchorZoneMatchZoneMappingModelList = data;
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

        $scope.updateAnchorZone = function () {
            if ($scope.id) {
                $http.put("/zone/anchor-zone", $scope.anchorZone).success(function () {
                    alert('更新成功');
                    $state.go("zone.anchor-zone.list");
                });
            } else {
                $http.post("/zone/anchor-zone", $scope.anchorZone).success(function (data) {
                    alert('保存成功');
                    $state.go("zone.anchor-zone.list");
                });
            }
        };

        $scope.uploadIcoImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.anchorZone.icoImage = data.path;
                    });
                }
            }
        };

        $scope.uploadBackgroundImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.anchorZone.backgroundImage = data.path;
                    });
                }
            }
        };

        $scope.uploadRecommendImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.anchorZone.recommendImage = data.path;
                    });
                }
            }
        };

        $scope.saveAnchorZoneHonor = function () {
            if ($scope.anchorZoneHonor) {
                $scope.anchorZoneHonorList.push(angular.copy($scope.anchorZoneHonor));
                $scope.anchorZone.anchorZoneHonorList = $scope.anchorZoneHonorList;
                $scope.anchorZoneHonor = {};
            }
        };
        $scope.deleteAnchorZoneHonor = function (anchorZoneHonor) {
            for (var i = 0; i < $scope.anchorZoneHonorList.length; i++) {
                if ($scope.anchorZoneHonorList[i] == anchorZoneHonor) {
                    $scope.anchorZoneHonorList.splice(i, 1);
                }
            }
            $scope.anchorZone.anchorZoneHonorList = $scope.anchorZoneHonorList;

        }


    }]);
})(angular);