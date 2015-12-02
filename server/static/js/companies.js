/**
 * Created by Qing on 2015/9/19.
 */
var company = angular.module("view.company", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/companies", {
            templateUrl: "/static/templates/companies.html"
        })
    }]).controller("companyCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
        $scope.welcome = "hello wa";
        $scope.code = $routeParams.code;
        $scope.companies = {};

        httpUtil.post("companies", function (code, data) {
            console.log(data);
            if (code == 0) {
                $scope.companies = data;
            }
        })
    }]);