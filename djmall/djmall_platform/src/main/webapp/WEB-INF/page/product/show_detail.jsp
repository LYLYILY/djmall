<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>商品sku展示</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>

</head>
<body>
<fieldset style="width: 600px; height: 350px">
    <legend>商品信息</legend>
    <form id="fm">
        <input type="hidden" name="userId" id="userId">
        <input type="hidden" name="buyStatus" value="1">
    <div id="dd">
    </div>
    选择商品信息<br><br>
    <c:forEach items="${product}" var="p">
    <input type="radio" value="${p.id}" name="skuId" id="skuName" <c:if test="${p.isDefault == 'ISDEFAULT'}" >checked</c:if> onclick="getProductBySkuName(this.value)">${p.skuName}&nbsp&nbsp
    </c:forEach><br><br>
    购买数量：
        <input type="button" value="-" onclick="plus()"/>
        <input type="text" id="buyNum" name="buyNum" value="1">
        <input type="button" value="+" onclick="shear()"/><br>
        <div id="count" style="color: red; height: 20px"></div>
    </form>
    <input type="button" style="float: right" value="立即购买" onclick="addOrder()">
    <input type="button" style="float: right"  value="加入购物车" onclick="addShopCar()">
</fieldset><br>
<fieldset style="width: 600px; height: 350px">
    <legend>商品评论</legend>
        好评率：
    <hr>
    <c:forEach items="${comment}" var="c">
        ${c.userName}： ${c.star}<br>${c.creatTime}--${c.context}<br><hr>
    </c:forEach>
</fieldset>
</body>
<script>
    userId = Cookies.get("USER_ID");
    $("#userId").val(userId);

    $(function (){
        getProductBySkuName();
    })

    function getProductBySkuName() {
        var id = $('input[name="skuId"]:checked').val();
        $.post(
            "<%=request.getContextPath()%>/product/getProductById",
            {"id":id},
            function(result) {
                var html = "";
                html += "<div>";
                html += "<div style='float: left'>";
                html += "<img style='height: 300px; width: 300px;' src='"+result.data.productImgShow+"'>";
                html += "</div>";
                html += "名称："+result.data.productNameShow +"&nbsp&nbsp";
                html += "原价："+ result.data.skuPrice +"<br><br>";
                html += "折扣："+ result.data.skuRate + "&nbsp&nbsp";
                html += "邮费："+ result.data.freightShow +"<br><br>";
                html += "商品描述："+ result.data.productDescShow +"<br><br>";
                html += "<input type='hidden' value="+result.data.skuCount+" id='skuCount'>";
                html += "点赞量："+ result.data.likeNumShow + "&nbsp&nbsp";
                html += "评论量：" +"<br><br>";
                html += "</div>";
                $("#dd").html(html);
            }
        );
    }

    var Count = $("#buyNum").val()
    function shear(){
        if($("#skuCount").val() > 0 && Count < $("#skuCount").val()){
            $("#count").text("有货");
        }else if($("#skuCount").val() <= 0){
            $("#count").text("无货");
            return;
        }else if($("#skuCount").val() >= 200){
            $("#count").text("最多购买200件商品");
            return;
        }else {
            $("#count").text("库存不足");
            return;
        }
        Count++
        $("#buyNum").val(Count)
    }

    function plus(){
        if(Count == 1){
            layer.msg("最少买一个")
            return;
        }
        Count--
        if($("#skuCount").val() > 0 && Count <= $("#skuCount").val()){
            $("#count").text("有货");
        }else if($("#skuCount").val() <= 0){
            $("#count").text("无货");
            return;
        }else {
            $("#count").text("库存不足");
            return;
        }
        $("#buyNum").val(Count)
    }

    /**加入购物车 */
    function addShopCar(){
        $.post(
            "<%=request.getContextPath()%>/shop/addShopCar",
            $("#fm").serialize(),
            function (result){
                if(result.code == 200){
                    layer.msg("加入购物车成功");
                    location.href = "<%=request.getContextPath()%>/shop/toShopCar";
                }
            }
        );
    }

</script>
</html>
