/**
 * Created by Qing on 2015/11/13.
 */
app.controller("indexCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
    $scope.shCode = "hello , you are welcome";
    $scope.code = $routeParams.code;
}]);