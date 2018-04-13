$(function () {
    var  rows = $("#table").find("tr");
    for (var i=0;i<rows.length;i++){
        var state = rows.eq(i).find("td").eq(4).text();
        console.log(state);
        if (state=="审核通过"){
            rows.eq(i).find(".pass").attr("disabled",true);
        }
    }
    $("#passed").click(function () {
        //审核通过的机构，需要将审核进行隐藏
        $("#table").text("");//设为空
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node2 =  "</td>\n" +
            "            <td><button disabled=\"disabled\">通过</button></td>\n" +
            "        </tr>";
        $.ajax({
            type: 'post',
            url: '/ManagerMainPage/Pass',
            success: function (data) {
                var institutions = data.institutions;
                var ins="";
                for (var i=0;i<institutions.length;i++){
                    ins = ins+node+institutions[i].institutionName+node1;
                    ins = ins+institutions[i].address+node1;
                    ins = ins + institutions[i].description+node1;
                    ins = ins + institutions[i].teachpower+node1;
                    ins = ins + institutions[i].state;
                    ins = ins + node2;
                }
                $("#table").append(ins);
            },
            error: function (error) {
                alert("获取机构信息失败！");
                console.log(error);
            }
        })
    })
    $("#notPassed").click(function () {
        //未审核的机构
        $("#table").text("");//设为空
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node2 =  "</td>\n" +
            "            <td><button class=\"pass\">通过</button></td>\n" +
            "        </tr>";
        $.ajax({
            type: 'post',
            url: '/ManagerMainPage/noPass',
            success: function (data) {
                var institutions = data.institutions;
                var ins="";
                for (var i=0;i<institutions.length;i++){
                    ins = ins+node+institutions[i].institutionName+node1;
                    ins = ins+institutions[i].address+node1;
                    ins = ins + institutions[i].description+node1;
                    ins = ins + institutions[i].teachpower+node1;
                    ins = ins + institutions[i].state;
                    ins = ins + node2;
                }
                $("#table").append(ins);
            },
            error: function (error) {
                alert("获取机构信息失败！");
                console.log(error);
            }
        })
    })
    $(".pass").click(function () {
        //审核通过，
        var institution = {};
        var institutionName = $(this).parent().parent().find("td").eq(0).text();
        institution.institutionName = institutionName;
        $.ajax({
            type: 'post',
            url: '/ManagerMainPage/PassInstitution',
            data: institution,
            success: function (data) {
                alert("机构审核成功，邮件已发送")
            },
            error: function (error) {
                alert("机构审核失败！请稍后重试");
                console.log(error);
            }
        })
    })
    $("#search").click(function () {
        //弹出搜索框
    })
    $("#searchInstitution").click(function () {
        //进行搜索
    })

})