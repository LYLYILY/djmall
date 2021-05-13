<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>
</head>
<body>
订单编号:${orderDTO.orderNo}<br>
收货信息:${orderDTO.receiverName}-${orderDTO.receiverPhone}<br>
地址:${orderDTO.receiverProvince}-${orderDTO.receiverCity}-${orderDTO.receiverCounty}-${orderDTO.receiverDetail}<br>
商品信息:<br>
<table class="layui-table">
    <tr>
        <td>编号</td>
        <td>商品信息</td>
        <td>数量</td>
        <td>实际金额</td>
        <td>折扣</td>
    </tr>
    <tbody id="tb"></tbody>
</table><br>
下单时间:${orderDTO.createTime}<br>
支付方式:${orderDTO.payTypeShow}<br>
支付时间:${orderDTO.payTime}<br>
商品总金额:${orderDTO.totalMoney}<br>
运费:${orderDTO.totalFreight}<br>
实付金额;${orderDTO.totalPayMoney}
</body>
<script type="text/javascript">

    $(function(){
        show();
    })
    /** 展示*/
    function show(){
        $.post(
            "<%=request.getContextPath()%>/order/showOrderDetailTable2",
            {"orderNo":'${orderDTO.orderNo}'},
            function(result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var e =result.data[i];
                    html+="<tr>";
                    html+="<td>"+e.id+"</td>";
                    html+="<td>"+e.skuInfo+"</td>";
                    html+="<td>"+e.buyCount+"</td>";
                    html+="<td>"+e.payMoney+"</td>";
                    html+="<td>"+e.skuRate+"</td>";
                    html+="</tr>";
                }
                $("#tb").html(html);
            });
    }
</script>
</html>
