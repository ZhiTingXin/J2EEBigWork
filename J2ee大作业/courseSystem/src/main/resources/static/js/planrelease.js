$(function() {
    var code = cookie.get("Institution");
    var steps = $(".step-bar ul li");
// First step
    $(".firstNext").click(function () {
        $(steps[1])
            .find(".number")
            .addClass("active");
        $(steps[1])
            .find(".line")
            .addClass("line-active");
        $(".account-setup").css("left", "-4000px");
        $(".user-details").css("left", "calc(50% - 175px)");
        //将1设为激活状态
    })

    $(".secondNext").click(function () {
        $(steps[2])
            .find(".number")
            .addClass("active");
        $(steps[2])
            .find(".line")
            .addClass("line-active");
        $(".user-details").css("left", "-4000px");
        $(".finish-step").css("left", "calc(50% - 175px)");
    })
    $(".firstPrev").click(function () {
        $(steps[1])
            .find(".number")
            .removeClass("active");
        $(steps[1])
            .find(".line")
            .removeClass("line-active");
        $(".user-details").css("left", "4000px");
        $(".account-setup").css("left", "calc(50% - 175px)");
    })

    $(".secondPrev").click(function () {
        $(steps[2])
            .find(".number")
            .removeClass("active");
        $(steps[2])
            .find(".line")
            .removeClass("line-active");
        $(".finish-step").css("left", "4000px");
        $(".user-details").css("left", "calc(50% - 175px)");
    })

    $("#courses").click(function () {
        //点击机构课程
        var url = "http://localhost:8080/InstitutionMainPage?code="+code;
        window.location.href = url;
    })
    $("#planRelease").click(function () {
        //计划发布
        var url = "http://localhost:8080/PlanRelease";
        window.location.href = url;
    })
    $("#logManager").click(function () {
        //刷新时间
        var url = "http://localhost:8080/ClassLog";
        window.location.href = url;
    })
    $("#institutionInfo").click(function () {
        var url = "http://localhost:8080/InstitutionInfo";
        window.location.href = url;
    })
    $("#classNum").bind("input propertychange", function () {
        $("#classInfo").text("");
        var classNum = $(this).val();

        var nodeOne = "<div>\n" +
            "                    <label>";
        var nodeTwo = "</label>\n" +
            "                    <input class =\"className\" type=\"text\" placeholder=\"班级名称\" required=\"required\"/>\n" +
            "                    <input class =\"studentNum\" type=\"number\" placeholder=\"班级人数\" required=\"required\"/>\n" +
            "                    <input class =\"teacher\" type=\"text\" placeholder=\"教师\" required=\"required\"/>\n" +
            "                    <input class =\"price\" type=\"number\" placeholder=\"价格\" required=\"required\"/> \n" +
            "                </div>";
        var wholeNode;
        for (var i=0;i<classNum;i++){
            wholeNode = nodeOne + "班级" +  (i + 1) + nodeTwo;
            $("#classInfo").append(wholeNode);
        }
    });
    $("#finish").click(function () {
        //获取班级信息
        var classNum = $("#classNum").val();
        var classInfo = $("#classInfo");
        var name = classInfo.find(".className");
        var num = classInfo.find(".studentNum");
        var te = classInfo.find(".teacher");
        var pr = classInfo.find(".price");
        var le = $("#learnTime").val();
        var  classVOList ="";
        for(var i=0;i<classNum;i++){
            classVOList = classVOList+name.eq(i).val()+",";//获取班级名称
            classVOList = classVOList+num.eq(i).val()+",";
            classVOList = classVOList+te.eq(i).val()+",";
            classVOList = classVOList+pr.eq(i).val()+"-";
        }
        var courseVO = {};
        courseVO.learnTime = le;
        courseVO.courseName = $("#courseName").val();
        courseVO.courseDiscription = $("#description").val();
        courseVO.beginDate = $("#beginDate").val();
        courseVO.endDate = $("#endDate").val();
        courseVO.needNum = $("#needNum").val();
        courseVO.classVOList = classVOList;
        courseVO.courseClass = $("#courseClass").val();
        courseVO.institution = code;
        $.ajax({
            type: 'post',
            url: '/PlanRelease/ReleaseCourse',
            data: courseVO,
            success: function (data) {
                alert("发布成功!")
            },
            error: function (error) {
                alert("发布失败！请稍后重试");
                console.log(error);
            }
        })
    });

})