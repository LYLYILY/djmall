<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
    <meta charset="utf-8">
    <title>商品新增</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="productStatus" value="UP">
    <input type="hidden" name="skuList[0].isDefault" value="ISDEFAULT">
    名称：
        <input type="text" name="productName"><br>
    邮费：
    <select name="freightId">
        <c:forEach items="${freightAll}" var="f">
            <option value="${f.id}">${f.logisticsCompany}-<c:if test="${f.freight == 0}">包邮</c:if><c:if test="${f.freight != 0}">${f.freight}元</c:if></option>
        </c:forEach>
    </select><br>
    商品描述：
        <textarea rows="2" cols="15" name="productDisc"></textarea><br>
    商品图片：<input type="file" id="f" name="file" value="file"><br>
    分类：
        <select name="productType" id="productType" onchange="productTypes(this.value);" >
            <option value="">请选择</option>
                <c:forEach items="${productList}" var="p">
                    <option value="${p.code}" id="${p.code}">${p.dictName}</option>
                </c:forEach>
        </select><br>
    sku:<input type="button" value="+" onclick="add()">
        <input type="button" value="生成SKU" onclick="addSku()">
    <table border="1px">
        <tr>
            <th>属性名</th>
            <th>属性值</th>
        </tr>
        <tbody id="tb"></tbody>
    </table><br>
    生成后的SKU
    <table id="tb2" border="1px"  cellspacing="0">
        <tr>
            <th>编号</th>
            <th>SKU属性</th>
            <th>库存</th>
            <th>价格(元)</th>
            <th>折扣(%)</th>
        </tr>
    </table>
    <shiro:hasPermission name="PRODUCT_INSERT_BTN">
    <input type="button" value="新增" onclick="addAll()">
    </shiro:hasPermission>
</form>
</body>
<script>
    function productTypes(){
        var code = $("#productType").val();//得到第一个下拉列表的值
        $.post(
            "<%=request.getContextPath()%>/sku/findAttrValueByProductType",
            {"productType":code},
            function (result){
                var html = ""
                for (var i = 0; i < result.data.length; i++) {
                    html += "<tr>"
                    html += "<td>"+result.data[i].attrName+"</td>"
                    html += "<td>"
                    var a = result.data[i].attrValue.split(",")
                    for (var j = 0; j < a.length; j++) {
                        html += "<input type='checkbox' value='"+a[j]+"'>"
                        html += a[j]
                    }
                    html += "</td>"
                    html += "</tr>"
                }
                $("#tb").html(html);
            }
        );
    }

    function add() {
        var content = "属性名：<input type='text' id='name'><br>";
        content += "属性值：<input type='text' id='vals'>";
        content += "(多个值之间用,分割)";
        layer.open({
            type: 1,
            title:'添加属性',
            btn: ["确认", "取消"],
            content: content,
            area: ['500px', '300px'],
            yes: function(index, layero){
                    var html = ""
                    html += "<tr>"
                    html += "<td>"+$("#name").val()+"</td>"
                    html += "<td>"
                    var a = $("#vals").val().split(",")
                    for (var j = 0; j < a.length; j++) {
                        html += "<input type='checkbox' value='"+a[j]+"'>"
                        html += a[j]
                    }
                    html += "</td>"
                    html += "</tr>"
                $("#tb").append(html);
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        });
    }

    function addSku() {
        //获取tbody
        var tbody = $("#tb");
        //获取tbody中每一个<tr>
        var trs = $("#tb tr");
        //声明数组
        var attrValue = new Array();
        //遍历trs中每一个属性
        for (var i = 0; i < trs.length; i++) {
            var tr = trs[i];
            //获取每一行tr中选中的值
            var check = $(tr).find(":checked")
            if (check.length == 0) {
                continue;
            }
        //遍历选中的值，并放到attrValues
        var attrValues = new Array();
        for (var j = 0; j < check.length; j++) {
            attrValues.push(check[j].value);
            }
            //将attrValues放到attrValue
            attrValue.push(attrValues);
        }
        var skuList = dkej(attrValue);
        var html = "";
        for (var i = 0; i < skuList.length; i++) {
            html += "<tr>";
            html += "<td>" + (i + 1) + "</td>";
            html += "<td>" + skuList[i] + "<input type='hidden' name='skuList["+ i +"].skuName' value='"+ skuList[i]+ "'/></td>";
            html += "<td><input type='text' name='skuList["+ i +"].skuCount' value='10' width='10%'></td>";
            html += "<td><input type='text' name='skuList["+ i +"].skuPrice' value='100' width='10%'></td>";;
            html += "<td><input type='text' name='skuList["+ i +"].skuRate' value='10' width='10%'></td>";;
            html += "<td><input type='button' value='移除' onclick='delSku(this)'>" +
                "<input type='hidden' name='skuList["+ (i+1) +"].isDefault' value='NODEFAULT'>" +
                "<input type='hidden' name='skuList["+ i +"].skuStatus' value='UP'></td>";
            html += "</tr>";
        }
        $("#tb2").append(html);
    }
    function delSku(obj){
        $(obj).parent().parent().remove();
    }

    function dkej(d) {// d = 二维数组
        var total = 1;
        for (var i = 0; i < d.length; i++) {
            total *= d[i].length;
        }
        var e = [];
        var itemLoopNum = 1;
        var loopPerItem = 1;
        var now = 1;
        for (var i = 0; i < d.length; i++) {
            now *= d[i].length;
            var index = 0;
            var currentSize = d[i].length;
            itemLoopNum = total / now;
            loopPerItem = total / (itemLoopNum * currentSize);
            var myIndex = 0;
            for (var j = 0; j < d[i].length; j++) {
                for (var z = 0; z < loopPerItem; z++) {
                    if (myIndex == d[i].length) {
                        myIndex = 0;
                    }
                    for (var k = 0; k < itemLoopNum; k++) {
                        e[index] = (e[index] == null ? "" : e[index] + ":") + d[i][myIndex];
                        index++;
                    }
                    myIndex++
                }
            }
        }
        return e;
    }

    function addAll(){
        debugger
        var index = layer.load(0, {shade: 0.3});
        var formData = new FormData($("#fm")[0]);
        $.ajax({
            url:"<%=request.getContextPath()%>/product/addProductSku",
            type:"post",
            data:formData,
            contentType:false,
            processData: false,
            success: function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/product/toShow";
                }
                layer.close(index);
            }
        });
    }
</script>
</html>
