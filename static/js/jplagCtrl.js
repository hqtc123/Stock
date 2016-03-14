/**
 * Created by Qing on 2016/3/14.
 */
app.controller("jplagCtrl", ["$scope", "Upload", "httpUtil", "$location", function ($scope, Upload, httpUtil, $location) {
    $scope.searchAllusion = function () {
        httpUtil.post("api/jplag/search/" + $scope.keyword + "/" + $scope.interval, function (code, data) {
            if (code == 0) {
                $scope.count = data.count;
                $scope.poems = data.poems;
            }
        })
    };

    // upload later on form submit or something similar
    $scope.submit = function () {
        if ($scope.form.file.$valid && $scope.file) {
            $scope.upload($scope.file);
        }
    };
     $scope.uploadFiles = function(file, errFiles) {
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            file.upload = Upload.upload({
                url: 'https://angular-file-upload-cors-srv.appspot.com/upload',
                data: {file: file}
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    file.result = response.data;
                });
            }, function (response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                file.progress = Math.min(100, parseInt(100.0 *
                                         evt.loaded / evt.total));
            });
        }
    }

}]);