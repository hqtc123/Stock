/**
 * Created by Qing on 2015/11/13.
 */
var indexModule = angular.module("view.index", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/index/", {
            templateUrl: "/static/templates/index.html"
        })
    }]).controller("companyCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
        $scope.shCode = "hello , you are welcome";
        $scope.code = $routeParams.code;
        $scope.companies = {};

        httpUtil.post("companies", function (code, data) {
            console.log(data);
            if (code == 0) {
                $scope.companies = data;
            }
        })
    }]);