$(function () {
    var student = cookie.get("loginStudent");
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
})