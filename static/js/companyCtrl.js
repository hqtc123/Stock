/**
 * Created by Qing on 2015/9/19.
 */
app.controller("companyCtrl", ["$scope", "$routeParams", "httpUtil", function ($scope, $routeParams, httpUtil) {
    $scope.welcome = "hello , you are welcome";
    $scope.code = $routeParams.code;
    $scope.name = "";
    $scope.dateArr = [];
    $scope.priceArr = [];
}]);

app.directive('kgraph', ["httpUtil", function (httpUtil) {
    return {
        scope: {
            id: "@",
            data: "=",
            code: "=",
            name: "=",
            dates: "=",
            prices: "="
        },
        restrict: 'E',
        template: '<div style="height:400px;width:900px"></div>',
        replace: true,
        link: function ($scope, element, attrs, controller) {
            var myChart = echarts.init(document.getElementById($scope.id), 'macarons');
            myChart.showLoading({text: "数据正在努力加载中……"});
            httpUtil.post("api/company/get/" + $scope.code, function (code, data) {
                if (code == 0) {
                    $scope.name = data.name;
                    console.log($scope.name);
                    httpUtil.post("api/company_price/" + $scope.code, function (code, data) {
                        if (code == 0) {
                            for (var x in data) {
                                $scope.dates.push(data[x].date);
                                $scope.prices.push([data[x].start, data[x].now, data[x].lowest, data[x].highest])
                            }


                            var option = {
                                title: {
                                    text: ""
                                },
                                tooltip: {
                                    trigger: 'axis',
                                    formatter: function (params) {
                                        var res = params[0].seriesName + ' ' + params[0].name;
                                        res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
                                        res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
                                        return res;
                                    }
                                },
                                legend: {
                                    data: [$scope.name]
                                },
                                toolbox: {
                                    show: true,
                                    feature: {
                                        mark: {show: true},
                                        dataZoom: {show: true},
                                        dataView: {show: true, readOnly: false},
                                        magicType: {show: true, type: ['line', 'bar']},
                                        restore: {show: true},
                                        saveAsImage: {show: true}
                                    }
                                },

                                // 横轴坐标轴
                                xAxis: [
                                    {
                                        type: 'category',
                                        boundaryGap: true,
                                        axisTick: {onGap: false},
                                        splitLine: {show: false},
                                        data: $scope.dates
                                    }
                                ],
                                // 纵轴坐标轴
                                yAxis: [
                                    {
                                        type: 'value',
                                        scale: true,
                                        boundaryGap: [0.01, 0.01]
                                    }
                                ], dataZoom: {
                                    show: true,
                                    realtime: true,
                                    start: 50,
                                    end: 100
                                },
                                // 数据内容数组
                                series: [
                                    {
                                        name: $scope.name,
                                        type: 'k',
                                        data: $scope.prices
                                    }
                                ]
                            };

                            myChart.hideLoading();
                            myChart.setOption(option);
                        }
                    });
                }
            });
        }
    };
}]);
