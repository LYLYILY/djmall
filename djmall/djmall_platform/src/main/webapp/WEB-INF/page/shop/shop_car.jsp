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
    全选<input type="checkbox" id="checks" onclick="checkeds()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="javascript:void(0)" onclick="del()" style="color: #1e69ff; font-size: 10px">删除选中商品</a>
    <div id="fs"></div>
        已选择<span style="color: #ff0000" id="number">0</span>件商品，商品金额：￥<span id="productPrice">0</span>，商品折后金额：￥<span id="ratePrice">0</span>，运费：￥<span id="receiveFee">0</span><br>
        应付金额：<span style="color: #ff0000">￥</span><span id="price" style="color: red">0</span><br>
    <input type="button" value="去结算" onclick="toSettleAccounts()" class="layui-btn layui-btn-normal layui-btn-sm">
</form>
</body>
<script>
    $(function (){
        show();
    });

    //全选全不选
    function checkeds(){
        var checks = document.getElementsByClassName("id");
        if ($("#checks").is(':checked')){
            for (var i = 0; i < checks.length; i++) {
                checks[i].checked = true;
            }
        }else{
            for (var i = 0; i < checks.length; i++) {
                checks[i].checked = false;
            }
        }
        checkbox();
    }


    //展示
    function show(){
        $.post(
            "<%=request.getContextPath()%>/shop/show",
            {
                "userId": Cookies.get("USER_ID"),
                "buyStatus":1
            },
            function (result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }else if (result.data == null){
                    layer.msg("暂无商品");
                }else{
                    var html = "";
                    for (var i = 0; i < result.data.length; i++) {
                        var e = result.data[i];
                        html+="<fieldset style='width: 500px'>";
                        html+="<legend>商品信息</legend>";
                        html+="<input onclick='checkbox("+e.id+")' class='id' type='checkbox' name='list["+e.id+"].id' id='box"+e.id+"' value='"+ e.id +"'/><input type='hidden' id='"+e.id+"' value='"+e.buyNum+"'><br>";
                        html+="名称:" + e.productName + " &nbsp;&nbsp; ";
                        html+="原价:￥" + e.skuPrice + " &nbsp;&nbsp; ";
                        if (e.skuRate == 0){
                            html+="折扣: 无，按照原价 &nbsp;&nbsp;";
                        }else{
                            html+="折扣:" + (100-e.skuRate) +"% &nbsp;&nbsp; ";
                        }
                        html+=" &nbsp; " + e.skuName +" &nbsp;&nbsp; ";
                        if (e.freight == 0){
                            html+="邮费: 包邮 &nbsp;";
                        }else{
                            html+="邮费: 平邮," + e.freight  + "元 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
                        }
                        html+="现价:￥<span style='color: red'>" +(100-e.skuRate)/100*e.skuPrice + "</span> &nbsp; <br>";
                        html+="数量:<input type='button' style='width: 25px' id='down"+e.id+"' value='-' onclick='checkNum("+ e.skuId +", false, "+e.id+")'/><input type='text' readonly='readonly' id='buyNum"+e.id+"' name='list["+e.id+"].buyNum' style='width: 50px;' value='"+ e.buyNum+"'/><input style='width: 25px' type='button' value='+' id='up"+e.id+"' onclick='checkNum("+ e.skuId +", true, "+e.id+")'/> &nbsp;";
                        html+="<span id='num"+e.id+"' style='color: red'></span>";
                        html+="<br><a href='javascript:void(0)' onclick='delShopCar("+ e.id +")' style='color: #1e69ff; font-size: 10px'>后悔了，不要了</a>"
                        html+="</fieldset>";
                    }
                    $("#fs").html(html);
                }

            }
        );
    }

    //数量
    function checkNum(skuId, yesOrNo, id){
        buyNum = $("#buyNum"+id).val();
        if (yesOrNo){
            buyNum ++;
            $("#buyNum"+id).val(buyNum);
        }else{
            if (buyNum > 1){
                buyNum --;
                $("#buyNum"+id).val(buyNum);
            }
        }

        $.post(
            "<%=request.getContextPath()%>/shop/checkNum",
            {"skuId": skuId},
            function(result){
                if (result.data.skuCount >= buyNum){
                    $("#num"+id).text("(有货)");
                    if ($("#buyNum"+id).val() >= 200){
                        $("#buyNum"+id).val(200);
                    }
                    document.getElementById("down"+ id).removeAttribute("disabled");
                    document.getElementById("up"+ id).removeAttribute("disabled");
                }else{
                    if(result.data.skuCount = 0){
                        $("#num"+id).text("(无货)");
                        document.getElementById("down"+ id).setAttribute("disabled", true)
                        document.getElementById("up"+ id).setAttribute("disabled", true)
                    }else if(result.data.skuCount < buyNum){
                        $("#num"+id).text("库存不足");
                        document.getElementById("up"+ id).setAttribute("disabled", true)
                    }
                    buyNum --;
                    $("#buyNum"+id).val(buyNum);
                }
            }
        );
        checkbox();
    }

    //删除选中商品
    function del(){
        var ids = [];
        $("input[class='id']:checked").each(function(){
            ids.push(this.value);
        })
        if(ids.length<1){
            layer.msg("至少勾选一项");
            return;
        }
        $.post(
            "<%=request.getContextPath()%>/shop/delShopCar",
            {"ids[]" : ids},
            function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/shop/toShopCar";
                }
            }
        );
    }

    //后悔，不要了
    function delShopCar(id){
        $.post(
            "<%=request.getContextPath()%>/shop/delShopCar",
            {"ids[]" : id},
            function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/shop/toShopCar";
                }
            }
        );
    }

    function checkbox() {
        var number = 0;
        var productPrice = 0;
        var ratePrice = 0;
        var receiveFee = 0;
        var price = 0;
        var ids = [];
        $("input[class='id']:checked").each(function(){
            ids.push(this.value);
        })
        number = ids.length;
        $("#number").text(number);
        if (number == 0) {
            $("#productPrice").text(0);
            $("#ratePrice").text(0);
            $("#receiveFee").text(0);
            $("#price").text(0);
        }else {
        $.post(
            "<%=request.getContextPath()%>/shop/findShopByIds",
            {"ids[]" : ids},
            function(result) {
                if (result.code == 200){
                    var arr=result.data.sort(compare('id'));
                    for (var i = 0; i < arr.length; i++) {
                        var e = arr[i];
                        productPrice += (e.skuPrice*($("#buyNum"+ids[i]).val()));
                        ratePrice += (((100-e.skuRate)/100)*e.skuPrice*$("#buyNum"+ids[i]).val());
                        receiveFee += e.freight;
                    }
                    price = ratePrice + receiveFee;
                }
                $("#productPrice").text(productPrice);
                $("#ratePrice").text(ratePrice);
                $("#receiveFee").text(receiveFee);
                $("#price").text(price);
            })
        }
    }

    //数组遍历排序根据属性
    var compare = function (prop) {
        return function (obj1, obj2) {
            var val1 = obj1[prop];
            var val2 = obj2[prop];
            if (val1 < val2) {
                return -1;
            } else if (val1 > val2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    //去结算
    function toSettleAccounts(){
        var ids = [];
        $("input[class='id']:checked").each(function(){
            ids.push(this.value);
        })
        if(ids.length<1){
            layer.msg("至少勾选一项");
            return;
        }
        $.post(
            "<%=request.getContextPath()%>/shop/updateStatus",
            $("#fm").serialize(),
            function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/shop/toSettle/" + Cookies.get("USER_ID");
                }
            }
        );
    }


</script>
<style>
</style>
</html>
