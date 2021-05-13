<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>订单展示</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>
</head>
<body>
<div class="layui-tab layui-tab-card" lay-filter="demo">
    <ul class="layui-tab-title">
        <li class="layui-this">待付款</li>
        <li>待收货</li>
        <li>已完成</li>
        <li>已取消</li>
    </ul>
    <div class="layui-tab-content" style="height: 100px;">
        <div class="layui-tab-item layui-show">
            <table class="layui-table">
                <tr>
                    <td>订单号</td>
                    <td>商品名称</td>
                    <td>购买数量</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="tb1"></tbody>
            </table>

        </div>
        <div class="layui-tab-item">
            <table class="layui-table">
                <tr>
                    <td>订单号</td>
                    <td>商品信息</td>
                    <td>购买数量</td>
                    <td>折扣</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>付款时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="tb2"></tbody>
            </table>
        </div>
        <div class="layui-tab-item">
            <table class="layui-table">
                <tr>
                    <td>订单号</td>
                    <td>商品信息</td>
                    <td>购买数量</td>
                    <td>折扣</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>付款时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="tb3"></tbody>
            </table>
        </div>
        <div class="layui-tab-item">
            <table class="layui-table">
                <tr>
                    <td>订单号</td>
                    <td>商品名称</td>
                    <td>购买数量</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="tb4"></tbody>
            </table>
        </div>
        <div align="center" id="pageDiv"></div>
    </div>
</div>
<input type="hidden" id="status" value="ORDER_OBLIGATION" >
</body>
<script type="text/javascript">
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;
        element.on('tab(demo)', function(data){
            $("#tb1").empty();
            $("#tb2").empty();
            $("#tb3").empty();
            $("#tb4").empty();
            if (data.index == 0) {
                //订单待付款状态（展示主表）
                $("#status").val("ORDER_OBLIGATION");
                show(pageNo);
            } else if (data.index == 1) {
                //订单待收货状态（展示子表）
                $("#status").val("ORDER_WAIT_RECE");
                showSon(pageNo);
            } else if (data.index == 2) {
                //订单已完成状态（展示子表）
                $("#status").val("ORDER_COMPLETED");
                showSon(pageNo);
            } else if (data.index == 3) {
                //订单已取消状态（展示主表）
                $("#status").val("ORDER_CANCELED");
                show(pageNo);
            }
        });
    });


    var pageNo = 1;
    $(function () {
        show(pageNo);
    })

    /** 展示主表*/
    function show(pageNo) {
        $.post(
            "<%=request.getContextPath()%>/order/list?pageNo=" + pageNo,
            {"orderStatus": $("#status").val(), "userId": Cookies.get("USER_ID")},
            function (result) {
                if (result.code != 200) {
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                var pageHtml = "";
                for (var i = 0; i < result.data.records.length; i++) {
                    var e = result.data.records[i];
                    html += "<tr>";
                    html += "<td><span style='color: blue' onclick='showDetails2(\""+e.orderNo+"\")'>" + e.orderNo + "</span></td>";
                    html += "<td>" + e.productName + "</td>";
                    html += "<td>" + e.totalBuyCount + "</td>";
                    html += "<td>" + e.totalPayMoney + "</td>";
                    html += "<td>" + e.payType + "</td>";
                    if (e.totalFreight == 0) {
                        html += "<td>包邮</td>";
                    } else {
                        html += "<td>平邮，" + e.totalFreight + "</td>";
                    }
                    html += "<td>" + e.createTime + "</td>";
                    //html += "<td>" + e.orderStatus + "</td>";
                    if (e.orderStatus=="ORDER_CANCELED"){
                        html += "<td>" + e.payTime + "</td>";
                        html += "<td><a href='javascript:void(0)' onclick='again(\""+e.orderNo+"\")'>再次购买</a></td>";
                    }
                    if (e.orderStatus=="ORDER_OBLIGATION"){
                        html += "<td>"+'待支付';
                        html += ",<a href='javascript:void(0)' onclick='payMoney(\""+e.orderNo+"\")'>去支付</a><br>";
                        html += "<a href='javascript:void(0)' onclick='quxiao(\""+e.orderNo+"\")'>取消订单</a>";
                        html += "</td>";
                    }
                    html += "</tr>";
                }
                if (e.orderStatus=="ORDER_OBLIGATION"){
                    $("#tb1").append(html);
                }
                if (e.orderStatus=="ORDER_CANCELED"){
                    $("#tb4").append(html);
                }
                pageHtml += "<span onclick='page(" + result.data.pages + ")'>---点击查看更多---</span>"
                $("#pageDiv").html(pageHtml);
            });
    }

    function page(pages) {
        if (pageNo >= pages) {
            layer.msg("尾页", {icon: 0});
            $("#pageDiv").html("<span>---我是有底线的---</span>");
            $("#pageDiv").attr("style","color:gray");
            return;
        }
        pageNo++;
        layer.msg(pageNo, {icon: 1});
        show(pageNo);
    }

    /** 展示订单子表*/
    function showSon(pageNo) {
        $.post(
            "<%=request.getContextPath()%>/order/show?pageNo=" + pageNo,
            {"orderStatus": $("#status").val(), "userId": Cookies.get("USER_ID")},
            function (result) {
                if (result.code != 200) {
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                var pageHtml = "";
                for (var i = 0; i < result.data.records.length; i++) {
                    var e = result.data.records[i];
                    html += "<tr>";
                    html += "<td><span style='color: blue' onclick='showDetails(\""+e.orderNo+"\")'>" + e.orderNo + "</span></td>";
                    html += "<td>" + e.productName + "</td>";
                    html += "<td>" + e.totalBuyCount + "</td>";
                    html += "<td>" + e.skuRate + "</td>";
                    html += "<td>" + e.totalPayMoney + "</td>";
                    html += "<td>" + e.payTypeShow + "</td>";
                    if (e.totalFreight == 0) {
                        html += "<td>包邮</td>";
                    } else {
                        html += "<td>平邮，" + e.totalFreight + "</td>";
                    }
                    html += "<td>" + e.createTime + "</td>";
                    html += "<td>" + e.payTime + "</td>";
                    if (e.orderStatus=="ORDER_WAIT_RECE"){
                        html += "<td><a href=''>提醒卖家发货</a></td>";
                    }
                    if (e.orderStatus=="ORDER_COMPLETED"){
                        html += "<td><a onclick='toComment("+e.productId+")' target='_blank'>评价晒单</a>|<a href=''>再次购买</a></td>";
                    }
                    //html += "<td>" + e.orderStatus + "</td>";
                    html += "</tr>";
                }
                if (e.orderStatus=="ORDER_WAIT_RECE"){
                    $("#tb2").append(html);
                }
                if (e.orderStatus=="ORDER_COMPLETED"){
                    $("#tb3").append(html);
                }
                pageHtml += "<span onclick='page1(" + result.data.pages + ")'>---点击查看更多---</span>"
                $("#pageDiv").html(pageHtml);
            });

    }

    function page1(pages) {
        if (pageNo >= pages) {
            layer.msg("尾页", {icon: 0});
            $("#pageDiv").html("<span>---我是有底线的---</span>");
            $("#pageDiv").attr("style","color:gray");
            return;
        }
        pageNo++;
        layer.msg(pageNo, {icon: 1});
        showSon(pageNo);
    }

    function showDetails(orderNo) {
        layer.open({
            type: 2,
            title: '订单详情页面',
            shadeClose: false,
            shade: 0.5,
            area: ['600px', '400px'],
            content:"<%=request.getContextPath()%>/order/toShowDetails/"+orderNo
        });
    }

    function showDetails2(orderNo) {
        layer.open({
            type: 2,
            title: '订单详情页面',
            shadeClose: false,
            shade: 0.5,
            area: ['600px', '400px'],
            content:"<%=request.getContextPath()%>/order/toShowDetails2/"+orderNo
        });
    }

    function payMoney(orderNo) {
        $.post(
            "<%=request.getContextPath()%>/order/payMoney",
            {"orderNo":orderNo},
            function(result) {
                if (result.code == 200) {
                    parent.location.href = "<%=request.getContextPath()%>/index/toIndex";
                }
                layer.msg(result.msg);
            }
        )
    }

    function quxiao(orderNo) {
        $.post(
            "<%=request.getContextPath()%>/order/quxiao",
            {
                "orderNo":orderNo,
                "orderStatus":"ORDER_CANCELED"
            },
            function(result) {
                if (result.code == 200) {
                    parent.location.href = "<%=request.getContextPath()%>/index/toIndex";
                }
                layer.msg(result.msg);
            }
        )
    }

    function again(orderNo) {
        $.post(
            "<%=request.getContextPath()%>/order/again",
            {
                "orderNo":orderNo,
            },
            function(result) {
                if (result.code == 200) {
                    parent.location.href = "<%=request.getContextPath()%>/shop/toShopCar";
                }
                layer.msg(result.msg);
            }
        )
    }



</script>
</html>
