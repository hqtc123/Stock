/**
 * Created by Qing on 2015/9/19.
 */
var company = angular.module("view.company", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/company/:code", {
            templateUrl: "templates/company.html"
        })
    }]).controller("companyCtrl", ["$scope", "$routeParams", function ($scope, $routeParams) {
        $scope.welcome = "welcome";
        $scope.code = $routeParams.code;


    }]);