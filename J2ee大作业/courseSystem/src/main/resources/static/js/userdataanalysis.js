$(function () {
    $.ajax({
        type: 'post',
        url: '/DataAnalysis/getUserInfo',
        success: function (data) {
            var dom = document.getElementById("container");
            var myChart = echarts.init(dom);
            var app = {};
            option = null;
            app.title = '坐标轴刻度与标签对齐';
            option = {
                color: ['#3398DB'],
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ['非会员', '会员0', '会员1','会员2','会员3','会员4'],
                        axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value}',
                            textStyle: {
                                color: '#fff'
                            }
                        }
                    }
                ],
                series : [
                    {
                        name:'数量',
                        type:'bar',
                        barWidth: '60%',
                        data:[data.noVip, data.vip0, data.vip1,data.vip2,data.vip3,data.vip4]
                    }
                ]
            };
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
        },
        error: function (error) {
            alert("用户不存在！");
            console.log(error);
        }
    })
    $("#userData").click(function () {
        var url = "http://localhost:8080/UserDataAnalysis";
        window.location.href = url;
    })
    $("#institution").click(function () {
        var url = "http://localhost:8080/InstitutionData";
        window.location.href = url;
    })
    $("#account").click(function () {
        var url = "http://localhost:8080/AccountData";
        window.location.href = url;
    })
})