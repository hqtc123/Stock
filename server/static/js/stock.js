/**
 * Created by Qing on 2015/9/19.
 */
var GlobalVarialbles = {
    serverUrl: ""
};

var app = angular.module("StockApp", ["view.company", "view.index", "ngRoute", "HTTPUtilModule"]);

app.config(["$routeProvider", function ($routeProvider) {
    $routeProvider.otherwise({redirectTo: "/index/"})
}]);

var HTTPUtilModule = angular.module("HTTPUtilModule", []).config([
    "$httpProvider", function ($httpProvider) {
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.timeout = 2000;
    }
]).factory("httpUtil", ["$http", "$rootScope", "$q", function ($http, $rootScope, $q) {
    var networkErrorCode = 999999;
    var timeoutDefer = $q.defer();

    var _http = function (type, url, data, callback, async) {
        var networkError = false;
        setTimeout(function () {
            networkError = true;
            timeoutDefer.resolve();
        }, 5000);

        if (async == undefined)
            async = true;

        $http({
            url: GlobalVarialbles.serverUrl + url,
            method: type,
            data: data,
            async: async,
            timeout: timeoutDefer.promise,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            if (_.isFunction(callback)) {
                callback(data.code, data.data);
            }
        }).error(function (data, status) {
            if (_.isFunction(callback)) {
                if (networkError)
                    callback(networkErrorCode, "network_error");
                else
                    callback(data.code, data.data);
            }
            console.log("Visit " + url + " error");
        })
    };

    return {
        post: function (url, data, callback, async) {

            if (typeof data === 'function') {
                async = callback;
                callback = data;
                data = {};
            }

            _http("post", url, data, callback, async);
        },
        get: function (url, data, callback, async) {
            if (typeof data === 'function') {
                async = callback;
                callback = data;
                data = {};
            }
            _http("get", url, data, callback, async);
        }
    }
}]);