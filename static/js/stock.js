/**
 * Created by Qing on 2015/9/19.
 */
var GlobalVarialbles = {
    serverUrl: ""
};

var app = angular.module("StockApp", ["ui.bootstrap", "ngRoute", "HTTPUtilModule"]);


var HTTPUtilModule = angular.module("HTTPUtilModule", []).config([
    "$httpProvider", function ($httpProvider) {
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.timeout = 2000;
    }
]).factory("httpUtil", ["$http", "$rootScope", function ($http, $rootScope) {
    var networkErrorCode = 999999;
    var _http = function (type, url, data, callback, async) {
        var networkError = false;
        setTimeout(function () {
            networkError = true;
        }, 5000);

        if (async == undefined)
            async = true;

        $http({
            url: GlobalVarialbles.serverUrl + url,
            method: type,
            data: data,
            async: async,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            if (_.isFunction(callback)) {
                callback(data.code, data.data);
                console.log(type + " " + url + " success ");
            }
        }).catch(function (data, status) {
            if (_.isFunction(callback)) {
                console.log(type + " " + url + " error , data " + data + ", status " + status + "   ff  " + data.statusText);
                if (networkError)
                    callback(networkErrorCode, "network_error");
                else
                    console.log(data)
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