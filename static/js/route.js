/**
 * Created by Qing on 2015/12/6.
 */
app.config(["$routeProvider", function ($routeProvider) {
    $routeProvider.when("/index/", {
        templateUrl: "/static/templates/index.html",
        controller: "indexCtrl"
    }).when("/about/", {
        templateUrl: "/static/templates/about.html"
    }).when("/companies", {
        templateUrl: "/static/templates/companies.html"
    }).when("/company/:code", {
        templateUrl: "/static/templates/company.html"
    }).otherwise({redirectTo: "/index/"})
}]);
