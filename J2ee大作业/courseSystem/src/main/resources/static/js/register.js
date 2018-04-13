$(function () {
    $("#register").click(function () {
        var mail = $("#mail").val();
        var password1= $("#password1").val();
        var password2= $("#password2").val();
        if (password1!=password2){
            alert("两次输入密码不一致，请重新输入");
        }else{
            var user = {};
            user.mail=mail;
            user.password = password1;
            $.ajax({
                type: 'post',
                url: '/Register/AddUser',
                data: user,
                success: function (data) {
                    alert("注册成功，请到邮箱验证后登录！");
                },
                error: function (error) {
                    alert("注册失败！请重试");
                    console.log(error);
                }
            })
        }
    })

})