$(function () {
    $("#userData").click(function () {
        var url = "http://localhost:8080/UserDataAnalysis";
        window.location.href = url;
    })
    $("#institution").click(function () {
        var url = "http://localhost:8080/InstitutionData";
        window.location.href = url;
    })
    $("#account").click(function () {
        var url = "http://localhost:8080/AccountData";
        window.location.href = url;
    })
    var node1 = "<tr>\n" +
        "            <td>";
    var node2 = "</td>\n" +
        "            <td>"
    var node3 = "</td>\n" +
        "        </tr>"
    var account = "";
    $.ajax({
        type: 'post',
        url: '/DataAnalysis/getAccount',
        success: function (data) {
            $("#table").text("");
            var accounts = data.accounts;
            for (var i=0;i<accounts.length;i++){
                account =account+node1+accounts[i].time +node2;
                account = account+accounts[i].total+node3;
            }
            $("#table").append(account);
        },
        error: function (error) {
            alert("获取信息失败！");
            console.log(error);
        }
    })
});
