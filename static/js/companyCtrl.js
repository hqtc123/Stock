/**
 * Created by Qing on 2015/9/19.
 */
app.controller("companyCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
    $scope.welcome = "hello , you are welcome";
    $scope.code = $routeParams.code;
    $scope.prices = {};

    httpUtil.post("api/company_price/" + $scope.code, function (code, data) {
        console.log(data);
        if (code == 0) {
            $scope.prices = data;
        }
    })
}]);