$(function(){
   $("#login").click(function () {
        var mail = $("#mail").val();
        var password = $("#password").val();
        var userInfo = {};
        userInfo.mail = mail;
        userInfo.password = password;
        $.ajax({
            type: 'post',
            url: '/Login/Confirm',
            data: userInfo,
            success: function (data) {
                if (data==1){
                    cookie.delete("loginStudent");
                    cookie.set("loginStudent",mail,1);
                    window.location.href="http://localhost:8080/MainPage";
                }else if(data==0){
                    alert("登录失败，请检查用户名和密码后重新输入");
                }else {
                    alert("用户尚未激活，请到邮箱激活后重试");
                }
            },
            error: function (error) {
                alert("用户不存在！");
                console.log(error);
            }
        })
   });
});