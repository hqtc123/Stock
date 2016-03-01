/**
 * Created by Qing on 2015/12/6.
 */
app.config(["$routeProvider", function ($routeProvider) {
    $routeProvider.when("/about/", {
        templateUrl: "/static/templates/about.html"
    }).when("/companies", {
        templateUrl: "/static/templates/companies.html"
    }).when("/company/:code", {
        templateUrl: "/static/templates/company.html"
    }).when("/poem", {
        templateUrl: "/static/templates/poem.html"
    }).otherwise({redirectTo: "/companies/"})
}]);
