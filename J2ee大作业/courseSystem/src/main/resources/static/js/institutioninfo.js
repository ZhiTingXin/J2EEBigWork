$(function () {
    //机构信息
    //初始化机构信息
    var insti = cookie.get("Institution");
    var institution = {};
    institution.id = insti;
    $.ajax({
        type: 'post',
        url: '/InstitutionInfo/getInfo',
        data: institution,
        success: function (data) {
            //将信息填入框中，设定框为不可编辑
            $("#name").attr("readonly","readonly");
            $("#des").attr("readonly","readonly");
            $("#address").attr("readonly","readonly");
            $("#teach").attr("readonly","readonly");

            $("#des").text(data.description);
            $("#name").val(data.institutionName);
            $("#address").val(data.address);
            $("#teach").text(data.teachpower);
        },
        error: function (error) {
            alert("用户不存在！");
            console.log(error);
        }
    })
    var Ins = cookie.get("Institution");
    //菜单栏
    $("#courses").click(function () {
        //点击机构课程
        var url = "http://localhost:8080/InstitutionMainPage?code="+instis;
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
    $("#booked").click(function () {
        //点击查看预订信息
        var url = "http://localhost:8080/InstitutionBookedInfo?Institution="+Ins;
        window.location.href = url;
    })
    $("#canceled").click(function () {
        //查看退订信息
        var url =  "http://localhost:8080/InstitutionCanceledInfo?Institution="+Ins;
        window.location.href = url;
    })
    $("#account").click(function () {
        //查看财务信息
        var url = "http://localhost:8080/InstitutionAccountInfo?Institution="+Ins;
        window.location.href = url;
    })
    $("#edit").click(function () {
        //点击编辑
        $("#name").removeAttr("readonly");
        $("#des").removeAttr("readonly");
        $("#teach").removeAttr("readonly");
        $("#address").removeAttr("readonly");
    })
    
    $("#save").click(function () {
        //点击保存，保存机构信息
        var ins = {};
        ins.name = $("#name").val();
        ins.address = $("#address").val();
        ins.description = $("#des").text();
        ins.teachpower = $("#teach").text();
        ins.institution = insti;
        $.ajax({
            type: 'post',
            url: '/InstitutionInfo/saveIns',
            data: ins,
            success: function (data) {
                alert("保存成功！")
            },
            error: function (error) {
                alert("保存失败！");
                console.log(error);
            }
        })
    })


})