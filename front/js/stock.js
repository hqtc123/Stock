/**
 * Created by Qing on 2015/9/19.
 */
var GlobalVarialbles = {
    serverUrl: "http://127.0.0.1:5000"
};

var app = angular.module("StockApp", ["view.company", 'ngRoute']);

var httpUtil = angular.module("HTTPUtil", []).config([
    "$httpProvider", function ($httpProvider) {
        $httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.timeout = 2000;
    }
]).factory("stockHttp",["$http","$rootScope",function($http,$rootScope)])