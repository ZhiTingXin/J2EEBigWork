$(function () {
    var institution = cookie.get("Institution");
    var courseName = "";
    var table = $("#table").find("tr");
    for (var i=0;i<table.length;i++){
        if(table.eq(i).find("td").eq(7).text()=="正在进行"||table.eq(i).find("td").eq(7).text()=="已结束"){
            table.eq(i).find("td").find("button").attr("disabled",true);
        }
    }
    $("#logManager").click(function () {
        var url = "http://localhost:8080/ClassLog";
        window.location.href = url;
    })
    $("#planRelease").click(function () {
        var url = "http://localhost:8080/PlanRelease";
        window.location.href = url;
    })
    $("#courses").click(function () {
        var url = "http://localhost:8080/InstitutionMainPage?code="+institution;
        window.location.href = url;
    })
    $("#institutionInfo").click(function () {
        var url = "http://localhost:8080/InstitutionInfo";
        window.location.href = url;
    })
    $("#on").click(function () {
        //   查找进行中的课程
        $("#table").text("");//设为空
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node3 = "</td>\n" +
            "            <td>";
        var node4 = "</td>\n" +
            "            <td>";
        var node5 = "</td>\n" +
            "            <td>";
        var node6 = "</td>\n" +
            "            <td>";
        var node7 = "</td>\n" +
            "            <td>";
        var node8 =  "</td>\n" +
            "            <td><button class=\"book\" disabled=\"disabled\">预订课程</button></td>\n" +
            "        </tr>";
        var ins = {};
        ins.code = institution;
        $.ajax({
            type: 'post',
            url: '/InstitutionMainPage/on',
            data:ins,
            success: function (data) {
                var courses = data.courses;
                var course="";
                for (var i=0;i<data.courses.length;i++){
                    course = course+ node+courses[i].courseName+node1;
                    course = course+courses[i].courseDescription+node3;
                    course = course+courses[i].courseClass+node3;
                    course = course+courses[i].needNum+node3;
                    course = course+courses[i].lastNum+node4;
                    course = course+courses[i].beginDate+node5;
                    course = course+courses[i].endDate+node6;
                    course = course+courses[i].courseState+node7;
                    course = course+courses[i].learntime+node8;
                }
                $("#table").append(course);
            },
            error: function (error) {
                alert("获取正在进行课程失败！");
                console.log(error);
            }
        })
    })
    $("#noBegin").click(function () {
        //尚未开始
        $("#table").text("");//设为空
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node3 = "</td>\n" +
            "            <td>";
        var node4 = "</td>\n" +
            "            <td>";
        var node5 = "</td>\n" +
            "            <td>";
        var node6 = "</td>\n" +
            "            <td>";
        var node7 = "</td>\n" +
            "            <td>";
        var node8 =  "</td>\n" +
            "            <td><button class=\"book\">预订课程</button></td>\n" +
            "        </tr>";
        var ins = {};
        ins.code = institution;
        $.ajax({
            type: 'post',
            url: '/InstitutionMainPage/noBegin',
            data:ins,
            success: function (data) {
                var courses = data.courses;
                var course="";
                for (var i=0;i<data.courses.length;i++){
                    course = course+ node+courses[i].courseName+node1;
                    course = course+courses[i].courseDescription+node3;
                    course = course+courses[i].courseClass+node3;
                    course = course+courses[i].needNum+node3;
                    course = course+courses[i].lastNum+node4;
                    course = course+courses[i].beginDate+node5;
                    course = course+courses[i].endDate+node6;
                    course = course+courses[i].courseState+node7;
                    course = course+courses[i].learntime+node8;
                }
                $("#table").append(course);
            },
            error: function (error) {
                alert("获取课程信息失败！");
                console.log(error);
            }
        })
    })
    $("#finished").click(function () {
        //已结束课程
        $("#table").text("");//设为空
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node3 = "</td>\n" +
            "            <td>";
        var node4 = "</td>\n" +
            "            <td>";
        var node5 = "</td>\n" +
            "            <td>";
        var node6 = "</td>\n" +
            "            <td>";
        var node7 = "</td>\n" +
            "            <td>";
        var node8 =  "</td>\n" +
            "            <td><button class=\"book\" disabled=\"disabled\">预订课程</button></td>\n" +
            "        </tr>";
        var ins = {};
        ins.code = institution;
        $.ajax({
            type: 'post',
            url: '/InstitutionMainPage/off',
            data:ins,
            success: function (data) {
                var courses = data.courses;
                var course="";
                for (var i=0;i<data.courses.length;i++){
                    course = course+ node+courses[i].courseName+node1;
                    course = course+courses[i].courseDescription+node3;
                    course = course+courses[i].courseClass+node3;
                    course = course+courses[i].needNum+node3;
                    course = course+courses[i].lastNum+node4;
                    course = course+courses[i].beginDate+node5;
                    course = course+courses[i].endDate+node6;
                    course = course+courses[i].courseState+node7;
                    course = course+courses[i].learntime+node8;
                }
                $("#table").append(course);
            },
            error: function (error) {
                alert("获取课程失败！");
                console.log(error);
            }
        })
    })
    table.find("td").find("button").click(function () {
        //点击预订,弹出订单型号选择框,同时需要添加是否会员的选择框
        $(".user-details").css("left", "calc(50% - 175px)");
        $("#classInfo").text("");
        courseName = $(this).parent().parent().children().eq(0).text();
        $("#course").text("");
        var course={};
        course.courseName = courseName;
        var classes="<select id=\"class\">";
        var node1= "                <option value=\"";
        var node2 = "\">"
        var node3 = "</option>\n"
        var node4 = "</select>"
        var node5 = "<a id=\"detail\">班级详情</a>"

        $.ajax({
            type: 'post',
            url: '/MainPage/getClass',
            data: course,
            success: function (data) {
                for (var i = 0; i < data.classModels.length; i++) {
                    classes = classes + node1 + data.classModels[i].className + node2 + data.classModels[i].className + node3;
                }
                classes = classes + node4 + node5;
                $("#course").append(classes);
                $("#detail").click(function () {
                    //查看班级信息
                    var classInfo = {};
                    classInfo.className = $("#class").find("option:selected").text();//班级名称
                    classInfo.courseName = courseName;//课程名称
                    $.ajax({
                        type: 'post',
                        url: '/InstitutionMainPage/getClassInfo',
                        data: classInfo,
                        success: function (data) {
                            //将div显示到最上层
                            $(".user-details").css("left", "-4000px");
                            $(".finish-step").css("left", "calc(50% - 175px)");
                            $("#name").text(data.className);
                            $("#teacher").text(data.teacher);
                            $("#lastNum").text(data.lastNum);
                        },
                        error: function (error) {
                            alert("查看班级信息出错，请重试！");
                            console.log(error);
                        }
                    })
                })
            },
            error: function (error) {
                alert("查找班级失败！");
                console.log(error);
            }
        })
    })

    $(".secondPrev").click(function () {
        $(".finish-step").css("left", "4000px");
        $(".user-details").css("left", "calc(50% - 175px)");
    })
    $("#commit").click(function () {
        var num = $("#num").val();
        if (num==""){
            alert("将信息填写完整后再提交！");
        }else{
            //TODO 需要审核班级的信息是否足够订单数
            var className = $("#class").find("option:selected").text();//选择班级
            //将订单的信息传递到后端
            var order = {};
            order.courseName = courseName;//课程名称
            order.className = className;
            order.num = num;
            $.ajax({
                type: 'post',
                url: '/InstitutionMainPage/generateOrder1',
                data: order,
                success: function (data) {
                    alert("提交成功！")
                },
                error: function (error) {
                    alert("提交失败！");
                    console.log(error);
                }
            })
        }
        })
    $("#back").click(function () {
        $(".user-details").css("left", "1800px");//将div移出
    })
    $("#num").change(function () {
        //设置订单总价
        var num = $("#num").val();//数量
        $("#total").text("");
        var className = $("#class").find("option:selected").text();//选择班级
        var c = {};
        c.className =className;
        c.courseName = courseName;
        $.ajax({
            type: 'post',
            url: '/InstitutionMainPage/getClassInfo',
            data: c,
            success: function (data) {
                var total = data.price * num;
                $("#total").text(total);
            },
            error: function (error) {
                alert("查询价格出错！");
                console.log(error);
            }
        })
    })
})