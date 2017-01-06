(function (angular) {

    var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'ui.grid.selection',
        'ui.grid', 'ui.grid.pagination', 'ui.grid.resizeColumns',
        'validation', 'validation.rule', 'angular-loading-bar', 'ui.bootstrap.datetimepicker',
        'angularFileUpload']);


    app.factory('interceptors', ['$log', '$injector', '$q', function ($log, $injector, $q) {
        $log.debug('$log is here to show you that this is a regular factory with injection');

        var interceptors = {
            responseError: function (response) {
                if (response.status == 401) {
                    var $http = $injector.get('$http');
                    var $location = $injector.get('$location');
                    $http.get("login-url?path=" + encodeURIComponent(window.location.href)).success(function (data) {
                        window.open(data.loginUrl, "_self");
                    });
                    //如果本地登录，根据实际情况跳转
                    // $location.path("/login");
                    return response;
                }
                return $q.reject(response);

            }
        };

        return interceptors;
    }]);

    app.config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('index', {
                url: "/",
                // templateUrl: "index.html",
                controller: 'IndexController'
            })

            .state('website', {
                url: "/website",
                templateUrl: "www/view/module.view.html"
                //controller: 'IndexController'
            })

            .state('company-admin', {
                url: "/company-admin",
                templateUrl: "www/view/module.view.html"
                //controller: 'IndexController'
            })

            .state('admin', {
                url: "/admin",
                templateUrl: "www/view/module.view.html"
                //controller: 'IndexController'
            })

            .state('dealer', {
                url: "/dealer",
                templateUrl: "www/view/module.view.html"
            })

            .state('company', {
                url: "/company",
                templateUrl: "www/view/module.view.html"
                //controller: 'IndexController'
            })


            .state('admin.user', {
                url: "/user",
                templateUrl: "www/view/index.html",
                controller: 'SysUserController'
            })
            .state('admin.user.modify-password', {
                url: "/modify-password",
                templateUrl: "www/view/sys/user/modify-password.html",
                controller: 'SysUserController'
            })

            .state('admin.sys-user', {
                url: "/sys-user",
                templateUrl: "www/view/index.html",
                controller: 'SysUserController'
            })
            .state('admin.sys-user.list', {
                url: "/",
                templateUrl: "www/view/sys/user/list.html",
                controller: 'SysUserListController'
            })
            .state('admin.sys-user.update', {
                url: "/update",
                templateUrl: "www/view/sys/user/update.html",
                controller: 'SysUserUpdateController'
            })
            .state('admin.sys-user.reset-password', {
                url: "/reset-password",
                templateUrl: "www/view/sys/user/reset.password.html",
                controller: 'SysUserUpdateController'
            })

            .state('admin.sys-role', {
                url: "/sys-role",
                templateUrl: "www/view/index.html"
                //controller: 'SysRoleController'
            })
            .state('admin.sys-role.list', {
                url: "/",
                templateUrl: "www/view/sys/role/list.html",
                controller: 'SysRoleListController'
            })
            .state('admin.sys-role.update', {
                url: "/update",
                templateUrl: "www/view/sys/role/update.html",
                controller: 'SysRoleUpdateController'
            })
            .state('admin.sys-role.role-add-user', {
                url: "/role-add-user",
                templateUrl: "www/view/sys/role/role-add-user.html",
                controller: 'SysRoleAddUserController'
            })
            .state('admin.sys-role.role-add-menu', {
                url: "/role-add-menu",
                templateUrl: "www/view/sys/role/role-add-menu.html",
                controller: 'SysRoleAddMenuController'
            })
            .state('admin.sys-menu', {
                url: "/sys-menu",
                templateUrl: "www/view/index.html"
                //controller: 'SysRoleController'
            })
            .state('admin.sys-menu.list', {
                url: "/",
                templateUrl: "www/view/sys/menu/list.html",
                controller: 'SysMenuListController'
            })
            .state('admin.sys-menu.update', {
                url: "/update",
                templateUrl: "www/view/sys/menu/update.html",
                controller: 'SysMenuUpdateController'
            })
            .state('admin.template-message-config', {
                url: "/template-message-config",
                templateUrl: "www/view/sys/template-message-config/template-message-config.index.html",
                controller: "TemplateMessageConfigIndexController"
            })
            .state('admin.template-message-config.list', {
                url: "/",
                templateUrl: "www/view/sys/template-message-config/template-message-config.list.html",
                controller: "TemplateMessageConfigListController"
            })
            .state('admin.template-message-config.update', {
                url: "/update",
                templateUrl: "www/view/sys/template-message-config/template-message-config.update.html",
                controller: "TemplateMessageConfigUpdateController"
            })
            .state('zone', {
                url: "/zone",
                templateUrl: "www/view/module.view.html"
                //controller: 'IndexController'
            })
            .state('zone.match-zone', {
                url: "/match-zone",
                templateUrl: "www/view/zone/match-zone/index.html"
            })
            .state('zone.match-zone.list', {
                url: "/",
                templateUrl: "www/view/zone/match-zone/list.html",
                controller: 'MatchZoneListController'
            })
            .state('zone.match-zone.update', {
                url: "/update",
                templateUrl: "www/view/zone/match-zone/update.html",
                controller: 'MatchZoneUpdateController'
            })
            .state('zone.match-team', {
                url: "/match-team",
                templateUrl: "www/view/zone/match-team/index.html"
            })
            .state('zone.match-team.list', {
                url: "/",
                templateUrl: "www/view/zone/match-team/list.html",
                controller: 'MatchTeamListController'
            })
            .state('zone.match-team.update', {
                url: "/update",
                templateUrl: "www/view/zone/match-team/update.html",
                controller: 'MatchTeamUpdateController'
            })
            .state('recommend', {
                url: "/recommend",
                templateUrl: "www/view/module.view.html"
            })
            .state('recommend.recommend', {
                url: "/recommend",
                templateUrl: "www/view/recommend/recommend/index.html"
            })
            .state('recommend.recommend.list', {
                url: "/",
                templateUrl: "www/view/recommend/recommend/list.html",
                controller: 'RecommendListController'
            })
            .state('recommend.recommend.update', {
                url: "/update",
                templateUrl: "www/view/recommend/recommend/update.html",
                controller: 'RecommendUpdateController'
            })
            .state('recommend.advertising', {
                url: "/advertising",
                templateUrl: "www/view/recommend/advertising/index.html"
            })
            .state('recommend.advertising.list', {
                url: "/",
                templateUrl: "www/view/recommend/advertising/list.html",
                controller: 'AdvertisingListController'
            })
            .state('recommend.advertising.update', {
                url: "/update",
                templateUrl: "www/view/recommend/advertising/update.html",
                controller: 'AdvertisingUpdateController'
            })
            .state('zone.anchor-zone', {
                url: "/anchor-zone",
                templateUrl: "www/view/zone/anchor-zone/index.html"
            })
            .state('zone.anchor-zone.list', {
                url: "/",
                templateUrl: "www/view/zone/anchor-zone/list.html",
                controller: 'AnchorZoneListController'
            })
            .state('zone.anchor-zone.update', {
                url: "/update",
                templateUrl: "www/view/zone/anchor-zone/update.html",
                controller: 'AnchorZoneUpdateController'
            })
            .state('picture', {
                url: "/picture",
                templateUrl: "www/view/picture/index.html"
            })
            .state('picture.list', {
                url: "/",
                templateUrl: "www/view/picture/list.html",
                controller: 'PictureListController'
            })
            .state('picture.update', {
                url: "/update",
                templateUrl: "www/view/picture/update.html",
                controller: 'PictureUpdateController'
            })
            .state('info', {
                url: "/info",
                templateUrl: "www/view/info/index.html"
            })
            .state('info.list', {
                url: "/",
                templateUrl: "www/view/info/list.html",
                controller: 'InfoListController'
            })
            .state('info.update', {
                url: "/update",
                templateUrl: "www/view/info/update.html",
                controller: 'InfoUpdateController'
            })
            .state('recommend.hot-keywords', {
                url: "/hot-keywords",
                templateUrl: "www/view/recommend/hot-keywords/index.html"
            })
            .state('recommend.hot-keywords.list', {
                url: "/",
                templateUrl: "www/view/recommend/hot-keywords/list.html",
                controller: 'HotKeywordsListController'
            })
            .state('recommend.hot-keywords.update', {
                url: "/update",
                templateUrl: "www/view/recommend/hot-keywords/update.html",
                controller: 'HotKeywordsUpdateController'
            })


    }).config(
        ['$httpProvider', function ($httpProvider) {
            $httpProvider.interceptors.push('interceptors');
        }]
    );

    app.run(function ($state, $rootScope, $http, i18nService) {
        i18nService.setCurrentLang('zh-cn');
        $rootScope.$state = $state;
        $http.get("/user/current").success(function (data) {
            $rootScope.user = data;
        });

        if (!$rootScope.imageServerUrl) {
            $http.get('image/server').success(function (urlResponse) {
                $rootScope.imageServerUrl = urlResponse.url;
            });
        }

        if (!$rootScope.mobilePath) {
            $http.get('mobilePath').success(function (m) {
                $rootScope.mobilePath = m.mobilePath;
            });
        }

    });

})(angular);