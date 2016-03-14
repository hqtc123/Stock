/**
 * Created by Qing on 2016/3/14.
 */
app.controller("jplagCtrl", ["$scope", "Upload", "httpUtil", "$location", "$http", function ($scope, Upload, httpUtil, $location, $http) {
    $scope.token = 5;
    $scope.codeTip = "选择zip格式的打包后的代码。";
    $scope.baseTip = "选择zip格式的打包后的Base代码,Base代码将会在查重时忽略。";

    $scope.codeFileName = "";
    $scope.baseFileName = "";

    $scope.languages = ["c/c++", "java 1.7", "c# 1.2"];
    $scope.language = "c/c++";
    $scope.searchAllusion = function () {
        httpUtil.post("api/jplag/search/" + $scope.keyword + "/" + $scope.interval, function (code, data) {
            if (code == 0) {
                $scope.count = data.count;
                $scope.poems = data.poems;
            }
        })
    };

    // upload later on form submit or something similar
    $scope.uploadCode = function (inputId) {
        var codeFile = document.getElementById(inputId).files[0];
        console.log(codeFile)
        var formData = new FormData();
        formData.append("file", codeFile);
        if (inputId == "code-file") {
            $scope.codeTip = "上传中。。。";
        } else {
            $scope.baseTip = "上传中。。。";
        }
        $http({
            method: 'POST',
            url: 'api/upload',
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function (data, status, headers, config) {
            if (data.code == 0) {
                if (inputId == "code-file") {
                    $scope.codeTip = "上传成功";
                    $scope.codeFileName = data.data.fileName;
                } else {
                    $scope.baseTip = "上传成功";
                    $scope.baseFileName = data.data.fileName;
                }
            } else {
                if (inputId == "code-file") {
                    $scope.codeTip = "上传失败！！";
                } else {
                    $scope.baseTip = "上传失败！！";
                }
            }
        }).error(function (data, status, headers, config) {
        });
    };

    $scope.runCheck = function () {
        params = {
            token: $scope.token,
            lang: $scope.language,
            codeFile: $scope.codeFileName,
            baseFile: $scope.baseFileName
        };

        httpUtil.post("api/jplag/check", params, function (code, data) {
            console.log(data)
        })
    }
}]);