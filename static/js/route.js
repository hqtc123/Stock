/**
 * Created by Qing on 2015/12/6.
 */
app.config(["$routeProvider", function ($routeProvider) {
    $routeProvider.when("/index/", {
        templateUrl: "/static/templates/index.html"
    }).when("/companies", {
        templateUrl: "/static/templates/companies.html"
    }).when("/company/:code", {
        templateUrl: "/static/templates/company.html"
    }).when("/poem", {
        templateUrl: "/static/templates/poem.html"
    }).when("/jplag", {
        templateUrl: "/static/templates/jplag.html"
    }).when("/allusion", {
        templateUrl: "/static/templates/allusion.html"
    }).when("/about", {
        templateUrl: "/static/templates/about.html"
    }).when("/game", {
        templateUrl: "/static/templates/game.html"
    }).otherwise({redirectTo: "/index/"})
}]);
