(function (angular) {

    var app = angular.module('app');

    app.run(function ($rootScope, $http) {
        $rootScope.topMenus = [];
        $rootScope.level2Menus = [];
        $rootScope.currentTopMenu = {};
        //$http.get("/level-1/menus").success(function (data) {
        //    $rootScope.topMenus = data;
        //    /*if ($rootScope.topMenus.length > 0) {
        //     $rootScope.loadLevel2($rootScope.topMenus[0].uuid);
        //     }*/
        //});


        $rootScope.loadLevel2 = function (uuid) {
            $http.get("/level-2/menus?parentUuid=" + uuid).success(function (data) {
                $rootScope.level2Menus = data;
            });
        }


        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            if (toState.name.indexOf($rootScope.currentTopMenu.url) == 0)return;
            $http.get("/level-1/menus").success(function (data) {
                $rootScope.topMenus = data;
                for (var i = 0; i < $rootScope.topMenus.length; i++) {

                    if ($rootScope.topMenus[i].url != "" && $rootScope.topMenus[i].url && toState.name.indexOf($rootScope.topMenus[i].url) == 0) {

                        $rootScope.currentTopMenu = $rootScope.topMenus[i];
                        $rootScope.loadLevel2($rootScope.topMenus[i].uuid);
                    }
                }
            });

        });

    });

    app.controller('IndexController', ['$scope', '$http', '$location', '$state', function ($scope, $http, $location, $state) {
        $scope.message = 'wechat-system';
    }]);

    app.directive('imgSrc', ['$rootScope', '$http', function ($rootScope, $http) {
        if (!$rootScope.imageServerUrl) {
            $http.get('image/server').success(function (urlResponse) {
                $rootScope.imageServerUrl = urlResponse.url;
            });
        }

        return {
            restrict: 'A',
            scope: {
                imgSrc: '=ngModel'
            },
            link: function (scope, element, attrs) {
                attrs.$observe('imgSrc', function (value) {
                    if (!value || value == '')
                        return;
                    var imgUrl;
                    var width = 120, height = 120;
                    if (element.is('img')) {
                        width = attrs.widths == undefined ? width : attrs.width.replace(/^.*\D+/g, '');
                        height = attrs.height == undefined ? height : attrs.height.replace(/^.*\D+/g, '');
                        imgUrl = $rootScope.imageServerUrl + '/image/' + width + 'x' + height + '/' + value;
                        element.attr('src', imgUrl);
                    } else if (element.is('a')) {
                        var imgElem = element.find('img');
                        if (imgElem) {
                            width = imgElem.attr('width') == undefined ? width : imgElem.attr('width').replace(/^.*\D+/g, '');
                            height = imgElem.attr('height') == undefined ? height : imgElem.attr('height').replace(/^.*\D+/g, '');
                        }
                        imgUrl = $rootScope.imageServerUrl + '/image/' + width + 'x' + height + '/' + value;
                        element.attr('href', imgUrl);
                    }
                });
            }
        };
    }]);

})(angular);