<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <script>
        function simpleName(name){
            nameList = name.split("(")
            headList = nameList[0].split(".")
            paramList = nameList[1].split(",")
            head = headList[headList.length - 2] + "." +headList[headList.length - 1]
            param = ""
            paramList.forEach(function (item) {
                itemList = item.split(".")
                param += itemList[itemList.length - 1]
            })
            return head+"("+param;
        }
        $(document).ready(function(){
            // $("#b01").click(function(){
            //     htmlobj=$.ajax({url:"/jquery/test1.txt",async:false});
            //     $("#myDiv").html(htmlobj.responseText);
            // });
            $.getJSON("../querystatistics.pinpoint",function(result){
                result.forEach(function (item,index,arr) {
                    console.log(simpleName(item.caller.name), simpleName(item.callee.name), item.count);
                    $caller = $("<td>"+simpleName(item.caller.name)+"</td>")
                    $callee = $("<td>"+simpleName(item.callee.name)+"</td>")
                    $count = $("<td>"+item.count+"</td>")
                    $tr = $("<tr></tr>").append($caller).append($callee).append($count)
                    $("tbody").append($tr)
                })
            });
        });
    </script>
</head>
<body>
<div>
<h2 class="sub-header">&nbspCall Statistics</h2>
&nbsp hap &nbsp &nbsp &nbsp
2018-12-01 —— 2018-12-15
<div width="80%">
    <table class="table table-striped">
        <thead>
        <tr>
            <%--<th width="10%">id</th>--%>
            <th width="40%"> Caller</th>
            <th width="40%">Callee</th>
            <th width="10%">count</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>
</body>

</html>