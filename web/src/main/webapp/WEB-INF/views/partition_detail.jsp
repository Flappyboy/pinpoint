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

    <script src="https://cdn.bootcss.com/echarts/4.2.0-rc.2/echarts.min.js"></script>
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
                    //console.log(simpleName(item.caller.name), simpleName(item.callee.name), item.count);
                    $caller = $("<td>"+simpleName(item.caller.name)+"</td>")
                    // $callee = $("<td>"+simpleName(item.callee.name)+"</td>")
                    $tr = $("<tr></tr>").append($caller)
                    $("tbody").append($tr)
                })
            });
        });
    </script>
</head>
<body>
<h2 class="sub-header">&nbspPartition</h2>
&nbsp first &nbsp &nbsp 2018-12-10 12:00:00
<div class="row">
    <div class="col-md-6">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th> Method</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
    <div class="col-md-6">
        <div id="main" style="width: 100%;height: 400px"></div>
    </div>
</div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    option = {
        title: {
            text: ''
        },
        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series : [
            {
                type: 'graph',
                layout: 'none',
                symbolSize: 50,
                roam: true,
                label: {
                    normal: {
                        show: true
                    }
                },
                edgeLabel: {
                    normal: {
                        textStyle: {
                            fontSize: 20
                        }
                    }
                },
                data: [{
                    name: 'A',
                    x: 300,
                    y: 300
                }, {
                    name: 'B',
                    x: 800,
                    y: 300
                }, {
                    name: 'C',
                    x: 550,
                    y: 100
                }, {
                    name: 'D',
                    x: 550,
                    y: 500
                }],
                // links: [],
                links: [ {
                    source: 'A',
                    target: 'C'
                }, {
                    source: 'B',
                    target: 'C'
                }, {
                    source: 'A',
                    target: 'D'
                }],
                lineStyle: {
                    normal: {
                        opacity: 0.9,
                        width: 2,
                        curveness: 0
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>

</html>