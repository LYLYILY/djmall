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
    订单编号:${orderInfoDTO.orderNo}<br>
    收货信息:${orderInfoDTO.receiverName}-${orderInfoDTO.receiverPhone}<br>
    地址:${orderInfoDTO.receiverProvince}-${orderInfoDTO.receiverCity}-${orderInfoDTO.receiverCounty}-${orderInfoDTO.receiverDetail}<br>
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
    下单时间:${orderInfoDTO.createTime}<br>
    支付方式:${orderInfoDTO.payTypeShow}<br>
    支付时间:${orderInfoDTO.payTime}<br>
    商品总金额:${orderInfoDTO.totalMoney}<br>
    运费:${orderInfoDTO.totalFreight}<br>
    实付金额;${orderInfoDTO.totalPayMoney}
</body>
<script type="text/javascript">

    $(function(){
        show();
    })
    /** 展示*/
    function show(){
        $.post(
            "<%=request.getContextPath()%>/order/showOrderDetailTable",
            {"orderNo":'${orderInfoDTO.orderNo}'},
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
