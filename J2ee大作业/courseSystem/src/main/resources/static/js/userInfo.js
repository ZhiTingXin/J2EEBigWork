$(function () {
    var student = cookie.get("loginStudent");
    var user ={};
    user.mail = student;
    $.ajax({
        type: 'post',
        url: '/UserInfo/getInfo',
        data: user,
        success: function (data) {
            if (data.vipState==false){
                $("#vipState").text("成为会员");
            }else {
                $("#vipState").text("注销会员");
            }
        },
        error: function (error) {
            alert("用户信息获取失败！");
            console.log(error);
        }
    })
    $("#userInfo").click(function () {
        //个人信息页面
        var url = "http://localhost:8080/UserInfo?user="+student;
        window.location.href = url;
    })
    $("#orderManager").click(function () {
        //order界面
        var url = "http://localhost:8080/MyOrder?mail="+student;
        window.location.href = url;
    })
    $("#myCourse").click(function () {
        //显示我的课程
        var url = "http://localhost:8080/UserCourseInfo?mail="+student;
        window.location.href = url;
    })
    $("#myGrade").click(function () {
        //显示我的成绩
        var url = "http://localhost:8080/UserGradeInfo?user="+student;
        window.location.href = url;
    })
    $("#myCost").click(function () {
        //显示我的消费信息
        var url = "http://localhost:8080/UserCostInfo?mail="+student;
        window.location.href = url;
    })
    $("#vipState").click(function () {
        var value = $("#vipState").text();
        if(value=="成为会员"){
            var stu = {};
            stu.mail = student;
            $.ajax({
                type: 'post',
                url: '/UserInfo/changeUserVipState',
                data: stu,
                success: function (data) {
                    alert("恭喜您成为本站会员，可以通过在本站预订课程来提升自己的会员等级，享受优惠");
                },
                error: function (error) {
                    alert("修改会员信息失败！请重试");
                    console.log(error);
                }
            })
        }else {
            //注销会员
            var stu = {};
            stu.mail = student;
            $.ajax({
                type: 'post',
                url: '/UserInfo/changeUserVipState',
                data: stu,
                success: function (data) {
                    alert("注销会员成功，之后在本站购票您将不会在享受会员优惠！");
                },
                error: function (error) {
                    alert("修改会员信息失败！请重试");
                    console.log(error);
                }
            })
        }
    })
    $("#edit").click(function () {
        //点击编辑
        $("#name").removeAttr("readonly");
        $("#phone").removeAttr("readonly");
    })
    $("#save").click(function () {
        var stu = {};
        stu.code = student;
        stu.name = $("#name").val();
        stu.phone = $("#phone").val();
        $.ajax({
            type: 'post',
            url: '/UserInfo/saveUser',
            data: stu,
            success: function (data) {
                alert("保存成功");
            },
            error: function (error) {
                alert("保存失败！");
                console.log(error);
            }
        })

    })
})