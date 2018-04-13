$(function () {
    var code = cookie.get("Institution");
    var ins = {};
    ins.code = code;
    $("#courseName").find("option").remove();
    //对于成绩信息只需要获取当前日期正在进行的课程
    var node = " <option value=\"";
    var next= "\">";
    var node1 = "</option>";
    var choices = "";
    $.ajax({
        type: 'post',
        url: '/InstitutionMainPage/on',
        data: ins,
        success: function (data) {
            var courses = data.courses;
            for (var i=0;i<courses.length;i++){
                choices=choices+node+courses[i].courseName+next+courses[i].courseName+node1;
            }
            $("#courseName").append(choices);
        },
        error: function (error) {
            alert("获取机构的课程信息失败！");
            console.log(error);
        }
    })
    var node2 = "            <tr>\n" +
        "                <td>";
    var node3 = "</td>\n" +
        "                <td><input type=\"checkbox\"/></td>\n" +
        "                <td><input type=\"text\"/></td>\n" +
        "            </tr>";

    $("#courseName").change(function () {
        $("#table").find("tr").remove();
        var studens = "";
        var c = {};
        c.course = $("#courseName").find("option:selected").text();
        $.ajax({
            type: 'post',
            url: '/ClassLog/GetStudents',
            data: c,
            success: function (data) {
                for(var i=0;i<data.stus.length;i++){
                    studens =studens+node2+data.stus[i].userName+node3;
                }
                $("#table").append(studens);
            },
            error: function (error) {
                alert("获取机构的课程信息失败！");
                console.log(error);
            }
        })
    })

    $("#courses").click(function () {
        //点击机构课程
        var url = "http://localhost:8080/InstitutionMainPage?code="+code;
        window.location.href = url;
    })
    $("#planRelease").click(function () {
        //计划发布
        var url = "http://localhost:8080/PlanRelease";
        window.location.href = url;
    })
    $("#logManager").click(function () {
        //刷新时间
        var url = "http://localhost:8080/ClassLog";
        window.location.href = url;
    })
    $("#institutionInfo").click(function () {
        var url = "http://localhost:8080/InstitutionInfo";
        window.location.href = url;
    })
    $("#commit").click(function () {
        //提交成绩，课程和时间不为空.
        var para = {};
        para.courseName =  $("#courseName").find("option:selected").text();
        para.time = $("#time").val();
        if(para.time==""){
            alert("请输入听课时间！");
        }else {
            var students = $("#table").find("tr");
            var stu  = "";
            var state = "";
            var grade = "";
            for (var i=0;i<students.length;i++){
                var stuname = students.find("td").eq(0).text();
                stu = stu + stuname +"-";
                console.log(stuname);
                var sta = students.find("td").eq(1).find("input").eq(0).is(':checked');
                var gra = students.find("td").eq(2).find("input").eq(0).val();//学生成绩
                state = state + sta+"-"
                grade = grade +gra+"-";
            }
            para.stu = stu;
            para.state = state;
            para.grade = grade;
            $.ajax({
                type: 'post',
                url: '/ClassLog/GenerateLog',
                data: para,
                success: function (data) {
                    alert("提交成功！");
                },
                error: function (error) {
                    alert("用户不存在！");
                    console.log(error);
                }
            })
        }

    })

})