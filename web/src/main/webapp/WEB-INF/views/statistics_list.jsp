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
        $(document).ready(function(){
        });
    </script>
</head>
<body>
<h2 class="sub-header">&nbspCall Statistics List</h2>
<div style="padding: 10px 0px 7px 10px">
    <button type="button" class="btn btn-primary btn-sm">Partition</button>
    <button type="button" class="btn btn-danger btn-sm">Delete</button>
</div>
<div width="80%">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>
                <input type="checkbox"/>
            </th>
            <th >id</th>
            <th >date</th>
            <th >from</th>
            <th >to</th>
            <th >to</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td> <input type="checkbox"/></td>
            <td>1</td>
            <td>2018-12-10 12:00:00</td>
            <td>2018-12-08 13:00:00</td>
            <td>2018-12-10 14:00:00</td>
            <td > <button type="button" class="btn btn-xs">detail</button></td>
        </tr>
        <tr>
            <td> <input type="checkbox"/></td>
            <td>2</td>
            <td>2018-12-13 12:00:00</td>
            <td>2018-12-08 13:00:00</td>
            <td>2018-12-14 14:00:00</td>
            <td > <button type="button" class="btn btn-xs">detail</button></td>
        </tr>
        </tbody>
    </table>
</div>
</body>

</html>