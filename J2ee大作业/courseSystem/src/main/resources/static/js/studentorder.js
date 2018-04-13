$(function () {
    var loginStudent = (cookie.get("loginStudent"));//当前登录用户
    var courseName = "";
    var orderId = "";
    var lastTime = [];

    $("#myCourse").click(function () {
        var url = "http://localhost:8080/MainPage";
        window.location.href = url;
    })
    $("#myInfo").click(function () {
        //我的会员信息
        var url = "http://localhost:8080/UserInfo?user="+loginStudent;
        window.location.href = url;
    })

    $("#orderInfo").click(function () {
        var url = "http://localhost:8080/MyOrder?mail="+loginStudent;
        window.location.href = url;
    })

    $("#bookedOrder").click(function () {
        //我预订的订单
        $("#tr").children("#cancel").remove();
        $("#tr").children("#lastTime").remove();
        $("#tr").children("#pay").remove();
        var add = "        <th id=\"cancel\">退订</th>";
        $("#tr").append(add);
        $("#table").text("");
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node2 =  "</td>\n" +
            "<td><button class =\"cancelOrder\">退订</button></td>"
            "        </tr>";
        var mail = {};
        mail.mail = loginStudent;
        $.ajax({
            type: 'post',
            url: '/MyOrder/Booked',
            data:mail,
            success: function (data) {
                var orders = data.orders;
                var order="";
                for (var i=0;i<data.orders.length;i++){
                    order = order+node+orders[i].orderid+node1+orders[i].courseName+node1+
                        orders[i].orderState+node1+orders[i].orderDate+node1+orders[i].classNum+node1+
                        orders[i].orderClass+node1+orders[i].total+node2;
                }
                $("#table").append(order);
                $(".cancelOrder").click(function () {
                    //点击退订，属于退订
                    var order = {};
                    order.mail = loginStudent;
                    order.orderId = $(this).parent().parent().find("td").eq(0).text();
                    $.ajax({
                        type: 'post',
                        url: '/MyOrder/CancelOrder',
                        data: order,
                        success: function (data) {
                            var str = "退订成功！为您账户返还金额："+data.total+"扣除用户积分："+data.integration;
                            alert(str);
                        },
                        error: function (error) {
                            alert("退订失败！请刷新后重试");
                            console.log(error);
                        }
                    })
                })
            },
            error: function (error) {
                alert("获取订单信息失败！");
                console.log(error);
            }
        })
    })
    $("#canceledOrder").click(function () {
        //退订订单
        $("#tr").children("#lastTime").remove();
        $("#tr").children("#pay").remove();
        $("#tr").children("#cancel").remove();
        $("#table").text("");
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node2 =  "</td>\n" +
            "        </tr>";
        var mail = {};
        mail.mail = loginStudent;
        $.ajax({
            type: 'post',
            url: '/MyOrder/Canceled',
            data:mail,
            success: function (data) {
                var orders = data.orders;
                var order="";
                for (var i=0;i<data.orders.length;i++){
                    order = order+node+orders[i].orderid+node1+orders[i].courseName+node1+
                        orders[i].orderState+node1+orders[i].orderDate+node1+orders[i].classNum+node1+
                        orders[i].orderClass+node1+orders[i].total+node2;
                }
                $("#table").append(order);
            },
            error: function (error) {
                alert("获取订单信息失败！");
                console.log(error);
            }
        })
    })
    $("#needPayOrder").click(function () {
        //未支付的订单
        $("#tr").children("#cancel").remove();
        $("#tr").children("#lastTime").remove();
        $("#tr").children("#pay").remove();
        var th ="        <th id=\"lastTime\">剩余支付时间</th>\n"+
            "        <th id=\"pay\">支付</th>";
        $("#tr").append(th);
        $("#table").text("");
        var node = "        <tr>\n" +
            "            <td>";
        var node1 = "</td>\n" +
            "            <td>";
        var node2 =  "</td>\n" +
            "            <td><button class=\"pay\">立即支付</button></td>\n" +
            "        </tr>";
        var mail = {};
        mail.mail = loginStudent;
        $.ajax({
            type: 'post',
            url: '/MyOrder/NotPayed',
            data: mail,
            success: function (data) {
                var orders = data.orders;
                var order="";
                for (var i=0;i<data.orders.length;i++){
                    lastTime.push(orders[i].millis);
                    order = order+node+orders[i].orderid+node1+orders[i].courseName+node1+
                        orders[i].orderState+node1+orders[i].orderDate+node1+orders[i].classNum+node1+
                        orders[i].orderClass+node1+orders[i].total+node1+node2;
                }
                $("#table").append(order);
                $(".pay").click(function () {
                    //点击立即支付，弹出支付界面，然后输入用户名密码和支付密码进行支付.进行会员优惠
                    $(".user-details").css("left", "calc(50% - 175px)");
                    //设置总价
                    var total = $(this).parent().parent().find("td").eq(6).text();
                    courseName = $(this).parent().parent().find("td").eq(1).text();
                    orderId = $(this).parent().parent().find("td").eq(0).text();
                    $("#total").text(total);
                    var userInfo = {};
                    userInfo.mail = loginStudent;
                    $.ajax({
                        type: 'post',
                        url: '/MyOrder/Dis',
                        data: userInfo,
                        success: function (data) {
                            var after;
                           if (data==10){
                               after = total*1;
                               $("#afterDis").text(after);
                           }else if(data==9) {
                               after = total*0.9;
                               $("#afterDis").text(after);
                           }else if(data==8){
                               after = total*0.8;
                               $("#afterDis").text(after);
                           }else if (data==7){
                               after = total*0.7;
                               $("#afterDis").text(after);
                           }else if (data==6){
                               after = total*0.6;
                               $("#afterDis").text(after);
                           }else {
                               after = total*0.5;
                               $("#afterDis").text(after);
                           }
                        },
                        error: function (error) {
                            alert("用户不存在！");
                            console.log(error);
                        }
                    })
                })
                window.setInterval(function () {
                    //为窗口设置定时器
                    var date = new Date();
                    var time = date.getTime();//获取当前时间
                    var rows = $("#table").find("tr");//所有的行
                    for(var i=0;i<rows.length;i++){
                        if (rows.eq(i).find("td").eq(2).text()=="未支付"){
                            //遍历每一行，只需要设置未支付的订单
                            var left = 60000-(time-lastTime[i]);//当前订单剩余mills
                            if (left<=0){
                                //表示剩余时间为0，将订单设为退订状态
                                var order = {};
                                order.orderId = rows.eq(i).find("td").eq(0).text();
                                order.mail =loginStudent;
                                $.ajax({
                                    type: 'post',
                                    url: '/MyOrder/CancelOrder',
                                    data: order,
                                    success: function (data) {
                                        //重新刷新表
                                    },
                                    error: function (error) {
                                        alert("获取用户信息失败！");
                                        console.log(error);
                                    }
                                })
                            }else {
                                //将剩余时间显示到屏幕
                                var minute = Math.floor(left / 60000);
                                var second = Math.floor((left-minute * 60000)/1000);
                                var str = minute+"分"+second+"秒";
                                rows.eq(i).find("td").eq(7).text(str);
                            }
                        }
                    }
                },1000);
            },
            error: function (error) {
                alert("获取订单信息失败！");
                console.log(error);
            }
        })
    })
    $("#search").click(function () {
        //显示搜索框

    })
    $("#searchCourse").click(function () {
        //显示搜索课程，id=table
        var course = $("#inputCourse").val();


    })
    $("#discount").change(function () {
        //用户使用积分进行抵扣
        $("#afterDiscount").text("");
        $("#discountInfo").text("");
        if ($(this).is(':checked')) {
            var student = {};
            student.mail = loginStudent;
            $.ajax({
                type: 'post',
                url: '/MyOrder/getIntegration',
                data: student,
                success: function (data) {
                    var dis = data/10;
                    var info = "您所拥有的积分为"+data+"可以抵扣"+dis+"元";
                    $("#discountInfo").text(info);
                    var after = $("#afterDis").text()-dis;
                    if(after>0) {
                        var what = "<label>抵扣后应付款：</label><label id=\"after\">" + after + "</label>";
                        $("#afterDiscount").append(what);
                    }else {
                        var what = "<label>抵扣后应付款：</label><label id=\"after\">" + "0" + "</label>";
                        $("#afterDiscount").append(what);
                    }
                },
                error: function (error) {
                    alert("获取用户信息失败！");
                    console.log(error);
                }
            })
        }
    })
    $("#commit").click(function () {
        //点击立即支付
        var discount = $("#discount").is(':checked');//是否采用积分进行支付
        if(discount){
            //采用积分支付，获取打折之后的钱数
            var needPay = $("#after").text();
            var pay = {};
            pay.username = $("#username").val();
            pay.password = $("#password").val();
            pay.payWord = $("#payWord").val();
            $.ajax({
                type: 'post',
                url: '/MyOrder/checkAccountInfo',
                data: pay,
                success: function (data) {
                    if (data<0){
                        alert("账户信息有误！请重新输入");
                    }else {
                        if (data<needPay){
                            alert("账户余额不足，请充值后进行支付！")
                        }else{
                            //进行支付，并生成log
                            var pay = {};
                            pay.total = $("#total").text();
                            pay.courseName = courseName;
                            pay.username = $("#username").val();
                            pay.orderId = orderId;
                            pay.needPay = needPay;
                            pay.mail = loginStudent;
                            $.ajax({
                                type: 'post',
                                url: '/MyOrder/payForTheOrderDis',
                                data: pay,
                                success: function (data) {
                                    alert("支付成功!");
                                },
                                error: function (error) {
                                    alert("支付失败！请稍后重试");
                                    console.log(error);
                                }
                            })
                        }
                    }
                },
                error: function (error) {
                    alert("账户信息有误！请重新输入");
                    console.log(error);
                }
            })
        }else {
            //没有使用积分进行支付
            var total = $("#afterDis").text();
            var pay = {};
            pay.username = $("#username").val();
            pay.password = $("#password").val();
            pay.payWord = $("#payWord").val();
            $.ajax({
                type: 'post',
                url: '/MyOrder/checkAccountInfo',
                data: pay,
                success: function (data) {
                    if (data<0){
                        alert("账户信息有误！请重新输入");
                    }else {
                        if (data<total){
                            alert("账户余额不足，请充值后进行支付！")
                        }else{
                            //进行支付，并生成log
                            var pay = {};
                            pay.total = total;
                            pay.courseName = courseName;
                            pay.username = $("#username").val();
                            pay.orderId = orderId;
                            pay.mail = loginStudent;
                            $.ajax({
                                type: 'post',
                                url: '/MyOrder/payForTheOrder',
                                data: pay,
                                success: function (data) {
                                    alert("支付成功!");
                                },
                                error: function (error) {
                                    alert("支付失败！请稍后重试");
                                    console.log(error);
                                }
                            })
                        }
                    }
                },
                error: function (error) {
                    alert("账户信息有误！请重新输入");
                    console.log(error);
                }
            })
        }

    })
    $("#back").click(function () {
        $(".user-details").css("left", "1800px");//将div移出
    })
})