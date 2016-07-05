/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('ArticleUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.article = {};
        $scope.original = angular.copy($scope.article);


        $scope.id = $location.search().articleId;
        $scope.files = [];
        $scope.articleTypes = [];
        $scope.articleCars = [];
        $scope.articleCarUuids = [];
        $scope.carBrandList = [];

        $scope.ue = UE.getEditor('container');

        var url = "/showCarSeries/listByBrandUuid";
        $http.get(url).success(function (data) {
            $scope.carBrandList = data;
            angular.forEach($scope.carBrandList, function (carBrand) {
                $scope.showCarSeriesList = carBrand.showCarSeriesList;
                angular.forEach($scope.showCarSeriesList, function (carSeries) {
                    carSeries.isChecked = false;
                });
            });

        });

        if ($location.search().articleId) {
            $http.get('article/' + $location.search().articleId).success(function (data) {
                $scope.article = data;
                $scope.ue.ready(function () {
                    $scope.ue.setContent($scope.article.content);
                });
                $http.get('/article/car-series/' + $scope.article.uuid).success(function (data) {
                    $scope.articleCars = data;

                    angular.forEach($scope.carBrandList, function (carBrand) {
                        $scope.showCarSeriesList = carBrand.showCarSeriesList;
                        angular.forEach($scope.showCarSeriesList, function (v1) {
                            angular.forEach($scope.articleCars, function (v2) {
                                if (v1.uuid == v2.carSeriesUuid)
                                    v1.isChecked = true;
                            });
                        });

                    });

                });
            });
        }

        $http.get('/article-type/listAll').success(function (data) {
            $scope.articleTypes = data;
        });

        $scope.selectArticleType = function (articleType) {
            $scope.article.typeUuid = articleType.uuid;
            $scope.article.typeName = articleType.name;
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

        $scope.clickCarSeries = function (series) {
            series.isChecked = !series.isChecked;
        };

        $scope.selectAll = function (selected, index) {
            if (selected) {
                angular.forEach($scope.carBrandList[index].showCarSeriesList, function (value) {
                    value.isChecked = true;
                });
            } else {
                angular.forEach($scope.carBrandList[index].showCarSeriesList, function (value) {
                    value.isChecked = false;
                });
            }
        }

        $scope.updateArticle = function () {
            if (!$scope.article.title || $scope.article.title.length < 6 || $scope.article.title.length > 20) {
                alert("标题最少6个字，最多20个字");
                return false;
            }
            if (!$scope.article.typeUuid) {
                alert("资讯类型不能为空");
                return false;
            }
            if (!$scope.article.coverImage) {
                alert("请上传文章图片");
                return false;
            }

            $scope.article.content = $scope.ue.getContent();

            $scope.articleCars = [];
            angular.forEach($scope.carBrandList, function (carBrand) {
                $scope.showCarSeriesList = carBrand.showCarSeriesList;
                angular.forEach($scope.showCarSeriesList, function (carSeries) {
                    if (carSeries.isChecked) $scope.articleCarUuids.push(carSeries.uuid);
                });
            });

            if (!$scope.articleCarUuids.length > 0) {
                alert("请选择关联车型");
                return false;
            }

            if ($scope.id) {
                $http.put("/article", $scope.article).success(function () {
                    $http.post("/article/car-series/" + $scope.article.uuid, $scope.articleCarUuids).success(function () {
                        alert('更新成功');
                        $state.go("wechat.article.list");
                    });

                });
            } else {
                $http.post("/article", $scope.article).success(function (data) {
                    $http.post("/article/car-series/" + data.uuid, $scope.articleCarUuids).success(function () {
                        alert('保存成功');
                        $state.go("wechat.article.list");
                    });

                });
            }
        };

        $scope.uploadCoverImage = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    $upload.upload({
                        url: 'image/upload',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data) {
                        $scope.article.coverImage = data.path;
                    });
                }
            }
        };

        $scope.LookArticle = function () {
            window.open($rootScope.mobilePath + "/article-detail/" + $scope.id);
        }

    }]);
})(angular);