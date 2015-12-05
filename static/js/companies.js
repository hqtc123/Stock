/**
 * Created by Qing on 2015/9/19.
 */
angular.module("view.companies", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/companies", {
            templateUrl: "/static/templates/companies.html"
        })
    }]).controller("companiesCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
        $scope.companies = {};

        httpUtil.post("companies", function (code, data) {
            console.log(data);
            if (code == 0) {
                $scope.companies = data;
            }
        })
    }]);