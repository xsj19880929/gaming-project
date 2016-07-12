/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('MatchTeamUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.matchTeam = {};

        $scope.id = $location.search().id;
        $scope.files = [];

        if ($location.search().id) {
            $http.get('zone/match-team/' + $location.search().id).success(function (data) {
                $scope.matchTeam = data;
            });
        } else {
            $http.get('zone/match-team/match-team-mapping-list').success(function (data) {
                $scope.matchTeam.matchTeamMappingModelList = data;
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

        $scope.updateMatchTeam = function () {
            if ($scope.id) {
                $http.put("/zone/match-team", $scope.matchTeam).success(function () {
                    alert('更新成功');
                    $state.go("zone.match-team.list");
                });
            } else {
                $http.post("/zone/match-team", $scope.matchTeam).success(function (data) {
                    alert('保存成功');
                    $state.go("zone.match-team.list");
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
                        $scope.matchTeam.icoImage = data.path;
                    });
                }
            }
        };


    }]);
})(angular);