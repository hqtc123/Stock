/**
 * Created by Qing on 2015/9/19.
 */
app.controller("companiesCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
        $scope.companies = {};
        httpUtil.post("companies", function (code, data) {
            console.log(data);
            if (code == 0) {
                $scope.companies = data;
            }
        })
    }]);