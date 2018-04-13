$(function() {
    var steps = $(".step-bar ul li");
// First step
    $("#login").click(function () {
        var url = "http://localhost:8080/InstitutionLogin";
        window.location.href = url;
    })
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
    $("#finish").click(function () {
        var institutionname = $("#institutionname").val();
        var institutionaddress = $("#institutionaddress").val();
        var mail = $("#mail").val();
        var institutionteach = $("#teachpower").val();
        var institutiondes = $("#description").val();
        if (institutionteach==""||institutionname==""||institutionaddress==""||institutiondes==""||mail==""){
            alert("请将信息填写完整后注册！");
        }else {
            console.log(institutionname);
            console.log(institutionaddress);
            console.log(institutiondes);
            console.log(institutionteach);
            console.log(mail);
            var  institute = {};
            institute.institutionName = institutionname;
            institute.address = institutionaddress;
            institute.teachpower = institutionteach;
            institute.description = institutiondes;
            institute.mail = mail;
            console.log(institute);
            $.ajax({
                type: 'post',
                url: '/InstitutionRegister/AddInstitution',
                data: institute,
                success: function (data) {
                    alert("您的申请已提交，审核通过后可在邮箱查看登录码进行登录！")
                },
                error: function (error) {
                    alert("申请失败，请重试！");
                    console.log(error);
                }
            })
        }

    })






})