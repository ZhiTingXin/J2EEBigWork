$(function () {
    $("#login").click(function () {
        var mail = $("#mail").val();
        var password = $("#password").val();
        var userInfo = {};
        userInfo.mail = mail;
        userInfo.password = password;
        $.ajax({
            type: 'post',
            url: '/ManagerLogin/Confirm',
            data: userInfo,
            success: function (data) {
                window.location.href="http://localhost:8080/ManagerMainPage";
            },
            error: function (error) {
                alert("登录失败！");
                console.log(error);
            }
        })
    })
})