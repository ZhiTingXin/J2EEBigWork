$(function () {
    $("#login").click(function () {
        //机构进行登录
        var institution = {};
        institution.code = $("#password").val();
        $.ajax({
            type: 'post',
            url: '/InstitutionLogin/InstitutionConfirm',
            data: institution,
            success: function (data) {
                if (data==0){
                    cookie.set("Institution",institution.code,1);
                    window.location.href="http://localhost:8080/InstitutionMainPage?code="+institution.code;
                }else{
                    alert("登录失败，请检查登录码后重新输入");
                }
            },
            error: function (error) {
                alert("登录失败，请检查登录码后重新输入！");
                console.log(error);
            }
        })

    })
})