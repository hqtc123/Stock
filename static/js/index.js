/**
 * Created by Qing on 2015/11/13.
 */
var indexModule = angular.module("view.index", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/index/", {
            templateUrl: "/static/templates/index.html"
        })
    }]);