(function (angular) {

    var app = angular.module('app');

    app.controller('SysMenuListController', ['$scope', '$rootScope', '$http', '$location', '$state', function ($scope, $rootScope, $http, $location, $state) {
        $http.get('/sys-menu/tree').success(function (data) {
            $scope.sysMenus = data;

        });


        $scope.toAdd=function(){
            $location.path('/admin/sys-menu/update');

        };

    }]);


})(angular);