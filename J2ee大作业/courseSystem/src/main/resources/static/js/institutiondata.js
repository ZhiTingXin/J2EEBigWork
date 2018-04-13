$(function () {

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
    $.ajax({
        type: 'post',
        url: '/DataAnalysis/getInstitutionInfo',
        success: function (data) {
            $("#insNum").text(data.insNum);
            $("#courseNum").text(data.courseNum);
            var dom = document.getElementById("container");
            var myChart = echarts.init(dom);
            var app = {};
            option = null;
            option = {
                backgroundColor: '#2c343c',
                title: {
                    text: '机构信息饼图',
                    left: 'center',
                    top: 20,
                    textStyle: {
                        color: '#ccc'
                    }
                },

                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },

                visualMap: {
                    show: false,
                    min: 80,
                    max: 600,
                    inRange: {
                        colorLightness: [0, 1]
                    }
                },
                series : [
                    {
                        name:'访问来源',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        data:[
                            {value:data.firstClass, name:'第一梯队，课程数小于5'},
                            {value:data.secondClass, name:'第二梯队，课程数大于5，小于10'},
                            {value:data.thirdClass, name:'第三梯队，课程数大于10'},
                        ].sort(function (a, b) { return a.value - b.value; }),
                        roseType: 'radius',
                        label: {
                            normal: {
                                textStyle: {
                                    color: 'rgba(255, 255, 255, 0.3)'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                lineStyle: {
                                    color: 'rgba(255, 255, 255, 0.3)'
                                },
                                smooth: 0.2,
                                length: 10,
                                length2: 20
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#c23531',
                                shadowBlur: 200,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },

                        animationType: 'scale',
                        animationEasing: 'elasticOut',
                        animationDelay: function (idx) {
                            return Math.random() * 200;
                        }
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
});