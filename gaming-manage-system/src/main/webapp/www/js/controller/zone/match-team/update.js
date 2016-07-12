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