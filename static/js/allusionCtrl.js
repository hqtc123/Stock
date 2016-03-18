/**
 * Created by Qing on 2016/3/16.
 */
app.controller("allusionCtrl", ["$scope", "$routeParams", "httpUtil", "$location", function ($scope, $routeParams, httpUtil, $location) {
    $scope.keyword = "一万枝";
    $scope.interval = -1;
    $scope.count = 0;
    $scope.poems = {}
    $scope.words = {}

    $scope.getWords = function () {
        httpUtil.post("/api/allusion/advice", function (code, data) {
            if (code == 0) {
                $scope.words = data;
            }
        })
    };


    $scope.searchAllusion = function (word) {
        $scope.keyword = word;
        httpUtil.post("api/poem/search/" + word + "/" + -1, function (code, data) {
            if (code == 0) {
                $scope.count = data.count;
                $scope.poems = data.poems;
            }
        })
    }

    $scope.getWords();
    $scope.searchAllusion($scope.keyword);
}]);