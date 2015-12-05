/**
 * Created by Qing on 2015/9/19.
 */
angular.module("view.company", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/company/:code", {
            templateUrl: "/static/templates/company.html"
        })
    }]).controller("companyCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
        $scope.welcome = "hello , you are welcome";
        $scope.code = $routeParams.code;
        $scope.companies = {};

    }]);