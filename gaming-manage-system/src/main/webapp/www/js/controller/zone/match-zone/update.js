/**
 *  Created by mandy.huang on 2016/5/10
 */
(function (angular) {
    var module = angular.module('app');

    module.controller('MatchZoneUpdateController', ['$scope', '$http', '$location', '$state', '$upload', '$rootScope', function ($scope, $http, $location, $state, $upload, $rootScope) {
        $scope.matchZone = {};

        $scope.id = $location.search().id;
        $scope.files = [];
        $scope.matchZoneArea = [];
        $scope.matchZoneYear = [];
        $scope.matchStatusList = [];
        $scope.matchTeamList = [];

        $scope.ue = UE.getEditor('container');

        $http.get('zone/match-zone/match-zone-area/list').success(function (data) {
            $scope.matchZoneAreaList = data;
        });

        $http.get('zone/match-zone/match-zone-year/list').success(function (data) {
            $scope.matchZoneYearList = data;
        });

        $http.get('/zone/match-zone/match-team/list').success(function (data) {
            $scope.matchTeamList = data;
        });
        $http.get('/enum/com.ygccw.wechat.common.zone.enums.MatchStatus').success(function (data) {
            $scope.matchStatusList = data;
        });
        if ($location.search().id) {
            $http.get('zone/match-zone/' + $location.search().id).success(function (data) {
                $scope.matchZone = data;
                $scope.ue.ready(function () {
                    $scope.ue.setContent($scope.matchZone.introduction);
                });
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

        $scope.updateMatchZone = function () {
            $scope.matchZone.introduction = $scope.ue.getContent();
            if ($scope.id) {
                $http.put("/zone/match-zone", $scope.matchZone).success(function () {
                    alert('更新成功');
                    $state.go("zone.match-zone.list");
                });
            } else {
                $http.post("/zone/match-zone", $scope.matchZone).success(function (data) {
                    alert('保存成功');
                    $state.go("zone.match-zone.list");
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
                        $scope.matchZone.icoImage = data.path;
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
                        $scope.matchZone.backgroundImage = data.path;
                    });
                }
            }
        };
        $scope.matchZoneBonus = {};
        $scope.matchZoneBonusList = [];
        $scope.saveMatchZoneBonus = function () {
            if ($scope.matchZoneBonus) {
                $scope.matchZoneBonusList.push(angular.copy($scope.matchZoneBonus));
                $scope.matchZone.matchZoneBonusList = $scope.matchZoneBonusList;
                $scope.matchZoneBonus = {};
            }
        };
        $scope.deleteMatchZoneBonus = function (matchZoneBonus) {
            for (var i = 0; i < $scope.matchZoneBonusList.length; i++) {
                if ($scope.matchZoneBonusList[i] == matchZoneBonus) {
                    $scope.matchZoneBonusList.splice(i, 1);
                }
            }
            $scope.matchZone.matchZoneBonusList = $scope.matchZoneBonusList;

        }

        $scope.matchZoneCalendar = {};
        $scope.matchZoneCalendarList = [];
        $scope.saveMatchZoneCalendar = function () {
            if ($scope.matchZoneCalendar) {
                for (var i = 0; i < $scope.matchTeamList.length; i++) {
                    if ($scope.matchZoneCalendar.matchTeamOneId == $scope.matchTeamList[i].id) {
                        $scope.matchZoneCalendar.matchTeamOneName = $scope.matchTeamList[i].name;
                    }
                    if ($scope.matchZoneCalendar.matchTeamTwoId == $scope.matchTeamList[i].id) {
                        $scope.matchZoneCalendar.matchTeamTwoName = $scope.matchTeamList[i].name;
                    }
                }
                $scope.matchZoneCalendarList.push(angular.copy($scope.matchZoneCalendar));
                $scope.matchZone.matchZoneCalendarList = $scope.matchZoneCalendarList;
                $scope.matchZoneCalendar = {};
            }
        };
        $scope.deleteMatchZoneCalendar = function (matchZoneCalendar) {
            for (var i = 0; i < $scope.matchZoneCalendarList.length; i++) {
                if ($scope.matchZoneCalendarList[i] == matchZoneCalendar) {
                    $scope.matchZoneCalendarList.splice(i, 1);
                }
            }
            $scope.matchZone.matchZoneCalendarList = $scope.matchZoneCalendarList;

        }

    }]);
})(angular);