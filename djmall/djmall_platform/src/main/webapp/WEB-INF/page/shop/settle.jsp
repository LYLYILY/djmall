<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>购物车</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" id="buyerId" name="buyerId">
    <input type="hidden" name="orderStatus" value="ORDER_OBLIGATION">
    <input type="hidden" id="totalMoney" name="totalMoney" >
    <input type="hidden" id="totalPayMoney" name="totalPayMoney" >
    <input type="hidden" id="totalfreight" name="totalFreight" >
    <input type="hidden" id="totalBuyCount" name="totalBuyCount" >
    收货人信息：<select name="receiveId">
    <c:forEach items="${address}" var="a">
        <option value="${a.id}">${a.addressee}-${a.phone}-${a.userProvince}${a.userCity}${a.userDistrict}${a.address}</option>
    </c:forEach>
</select>
    <a onclick="add()" href="javascript:void(0)" style="color: #1E9FFF">没有地址？去添加</a>
    <div id="fs"></div>
    共计<span style="color: red" id="number">0</span>件商品，商品金额：￥<span id="productPrice">0</span>，商品折后金额：￥<span id="ratePrice">0</span>，运费：￥<span id="receiveFee">0</span>，<br>
    应付金额：<span style="color: red">￥</span><span id="price" style="color: red">0</span><br>
    支付方式：<select name="payType">
    <c:forEach items="${pay}" var="p">
        <option value="${p.code}">${p.dictName}</option>
    </c:forEach>
</select><br>
    <input type="button" value="提交订单" onclick="tijiao()" class="layui-btn layui-btn-normal layui-btn-sm">
    <input type="button" value="取消订单" onclick="quxiao()" class="layui-btn layui-btn-normal layui-btn-sm">
</form>
</body>
<script>

    userId = Cookies.get("USER_ID");
    $("#buyerId").val(userId);

    $(
        function (){
            show();
        }
    );

    function show(){
        $.post(
            "<%=request.getContextPath()%>/shop/show",
            {
                "userId": Cookies.get("USER_ID"),
                "buyStatus":2
            },
            function (result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }else if (result.data == null){
                    layer.msg("暂无商品");
                }else{
                    var html = "";
                    var number = 0;
                    var productPrice = 0;
                    var ratePrice = 0;
                    var receiveFee = 0;
                    var price = 0;
                    for (var i = 0; i < result.data.length; i++) {
                        var e = result.data[i];
                        html+="<fieldset style='width: 500px'>";
                        html+="<legend>商品信息</legend>";
                        html+="<input type='hidden' name='list["+e.skuId+"].id' value='"+ e.skuId +"'/>名称:" + e.productName + " &nbsp;&nbsp; <input type='hidden' name='list["+e.skuId+"].productName' value='"+ e.productName+"'/>";
                        html+="原价:" + e.skuPrice + " &nbsp;&nbsp; <input type='hidden' name='list["+e.skuId+"].skuId' value='"+ e.skuId +"'/>";
                        if (e.skuRate == 0){
                            html+="折扣: 无，按照原价 &nbsp;&nbsp; <input type='hidden' name='list["+e.skuId+"].skuRate' value='"+ e.skuRate +"'/>";
                        }else{
                            html+="折扣:" + (100-e.skuRate) +"% &nbsp;&nbsp; ";
                        }
                        html+=" &nbsp; " + e.skuName +" &nbsp;&nbsp; ";
                        if (e.freight == 0){
                            html+="邮费: 包邮 &nbsp;<input type='hidden' name='list["+e.skuId+"].freight' value='"+ e.freight +"'/>";
                        }else{
                            html+="邮费: 平邮," + e.freight  + " &nbsp; <input type='hidden' name='list["+e.skuId+"].freight' value='"+ e.freight +"'/>";
                        }
                        html+="现价:<span>" +(100-e.skuRate)/100*e.skuPrice + "</span> &nbsp; <input type='hidden' name='list["+e.skuId+"].totalPayMoney' value='"+ (100-e.skuRate)/100*e.skuPrice +"'/> <br>";
                        html+="数量:  ×" + e.buyNum +"<input type='hidden' name='list["+e.skuId+"].buyCount' value='"+ e.buyNum +"'/>";
                        html+="<span id='num"+i+"' style='color: red'></span>";
                        html+="</fieldset>";
                        productPrice += (e.skuPrice*e.buyNum);
                        ratePrice += (((100-e.skuRate)/100)*e.skuPrice*e.buyNum);
                        if (e.buyNum == 0){
                            receiveFee = 0;
                        }else{
                            receiveFee += e.freight;
                        }
                        number += e.buyNum;
                    }
                    $("#fs").html(html);
                    price = ratePrice + receiveFee;
                    $("#productPrice").text(productPrice);
                    $("#ratePrice").text(ratePrice);
                    $("#receiveFee").text(receiveFee);
                    $("#price").text(price);
                    $("#number").text(number);
                    $("#totalMoney").val(productPrice);
                    $("#totalPayMoney").val(price);
                    $("#totalfreight").val(receiveFee);
                    $("#totalBuyCount").val(number);
                }
            }
        );
    }

    //添加收货地址
    function add(){
        layer.open({
            type: 2,
            title: '添加页面',
            shadeClose: false,
            shade: 0.5,
            area: ['300px', '400px'],
            content:"<%=request.getContextPath()%>/receiveAddress/toAddAddress"
        });
    }

    function tijiao(){
        $.post(
            "<%=request.getContextPath()%>/order/addOrder",
            $("#fm").serialize(),
            function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/index/toIndex";
                }
            }
        );
    }

    function quxiao(){
        $.post(
            "<%=request.getContextPath()%>/shop/updateBuyStatus",
            $("#fm").serialize(),
            function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/shop/toShopCar";
                }
            }
        );
    }
</script>
<style>
</style>
</html>
