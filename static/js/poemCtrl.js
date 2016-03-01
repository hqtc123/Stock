/**
 * Created by Qing on 2016/2/29.
 */
/**
 * Created by Qing on 2015/9/19.
 */
app.controller("poemCtrl", ["$scope", "$routeParams", "httpUtil", "$location", function ($scope, $routeParams, httpUtil, $location) {
    $scope.keyword = "";
    $scope.interval = -1;
    $scope.count = 0;
    $scope.poems = {}
    $scope.searchAllusion = function () {
        httpUtil.post("api/poem/search/" + $scope.keyword + "/" + $scope.interval, function (code, data) {
            if (code == 0) {
                $scope.count = data.count;
                $scope.poems = data.poems;
            }
        })
    };
}]);