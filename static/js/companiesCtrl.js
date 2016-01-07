/**
 * Created by Qing on 2015/9/19.
 */
app.controller("companiesCtrl", ["$scope", "$routeParams", "httpUtil", "$location", function ($scope, $routeParams, httpUtil, $location) {
    $scope.companies = {};
    $scope.currentPage = 1;
    $scope.loading = true;
    $scope.refreshCompanies = function () {
        $scope.loading = true;
        httpUtil.post("api/companies/" + $scope.currentPage, function (code, data) {
            if (code == 0) {
                $scope.loading = false;
                $scope.companies = data.companies;
                $scope.totalItems = data.totalCount;
            }
        })
    };
    $scope.refreshCompanies();
    $scope.totalItems = 0;
    $scope.setPage = function (pageNo) {
        $scope.currentPage = pageNo;
    };


    // search
    $scope.keyword = "";
    $scope.resultList = {};
    $scope.callSearch = function () {
        if ($scope.keyword.length < 2) {
            return;
        }
        httpUtil.post("api/companies/search/" + $scope.keyword, function (code, data) {
            if (code == 0) {
                $scope.resultList = data;
            }
        })
    }
    $scope.selectCompany = function (code) {
        $location.path("/company/" + code);
    }
}]);