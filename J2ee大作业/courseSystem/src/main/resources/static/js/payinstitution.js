$(function () {
    $("#save").click(function () {
        var pay = $("#pay").val();
        var p = {};
        p.pay = pay;
        $.ajax({
            type: 'post',
            url: '/PayInstitution/Save',
            data: p,
            success: function (data) {
                alert("保存成功！");            },
            error: function (error) {
                alert("保存失败！");
                console.log(error);
            }
        })
    })
})