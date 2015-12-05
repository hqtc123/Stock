/**
 * Created by Qing on 2015/12/5.
 */
angular.module("view.about", ["ngRoute"])
    .config(["$routeProvider", function ($routeProvider) {
        $routeProvider.when("/about", {
            templateUrl: "/static/templates/about.html"
        })
    }])