/**
 * Created by Qing on 2015/9/19.
 */
app.controller("companiesCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
    $scope.companies = {};
    $scope.page = 1;
    httpUtil.post("api/companies/" + $scope.page, function (code, data) {
        if (code == 0) {
            $scope.companies = data;
        }
    })
}]);