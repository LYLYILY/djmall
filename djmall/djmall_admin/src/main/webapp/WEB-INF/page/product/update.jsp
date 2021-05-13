<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>商品修改</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="id" value="${product.id}">
    <input type="hidden" name="productImg" value="${product.productImg}">
    名称：<input type="text" name="productName" value="${product.productName}"><br><br>
    邮费：<select name="freightId">
    <c:forEach items="${freightAll}" var="f">
        <option value="${f.id}" <c:if test="${f.id == product.freightId}">selected</c:if>>${f.logisticsCompany}-<c:if test="${f.freight == 0}">包邮</c:if><c:if test="${f.freight != 0}">${f.freight}元</c:if></option>
    </c:forEach>
</select><br><br>
    描述：<textarea rows="2" cols="15" name="productDisc">${product.productDisc}</textarea><br><br>
    图片：<img style="width: 50px; height: 50px" src="${product.productImg}">
    <input type="file" id="f" name="file" value="file"><br><br>
    <input type="hidden" name="productType" value="${product.productType}">
    分类：<select name="productType" id="productType" disabled="true">
    <option value="">请选择</option>
    <c:forEach items="${productList}" var="p">
        <option value="${p.code}" id="${p.code}" <c:if test="${product.productType == p.code}">selected</c:if>>${p.dictName}</option>
    </c:forEach>
</select><br><br>
    <table border="1px">
        SKU列表<input type="button"  value="修改库存" onclick="updateCount()">
        <input type="button"  value="编辑" onclick="bianji()">
        <input type="button"  value="设为默认" onclick="updateDefault()">
        <tr>
            <td>编号</td>
            <td>SKU属性</td>
            <td>库存</td>
            <td>价格(元)</td>
            <td>折扣(%)</td>
            <td>是否默认</td>
            <td>操作</td>
        </tr>
        <tbody id="tb"></tbody>
    </table><br>
    <input type="button" value="修改" onclick="update()">
</form>

</body>
<script>
    /** 修改*/
    function update(){
        var index = layer.load(0, {shade: 0.3});
        var formData = new FormData($("#fm")[0]);
        $.ajax({
            url:"<%=request.getContextPath()%>/product/update",
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

    /** sku展示*/
    $(function() {
        show();
    })

    function show() {
        $.post(
            "<%=request.getContextPath()%>/productSku/findSkuByProductId/" + '${product.id}',
            {},
            function(result) {
                var html="";
                for(var i=0;i<result.data.length;i++){
                    html+="<tr>";
                    html+="<td><input type='checkbox' name='id' value='"+result.data[i].id+"'/>"+result.data[i].id+"</td>";
                    html+="<td>"+result.data[i].skuName+"</td>";
                    html+="<td>"+result.data[i].skuCount+"</td>";
                    html+="<td>"+result.data[i].skuPrice+"</td>";
                    html+="<td>"+result.data[i].skuRate+"</td>";
                    if(result.data[i].isDefault == "ISDEFAULT") {
                        html+="<td>是</td>";
                    }else {
                        html+="<td>否</td>";
                    }
                    html+="<td><input type='button' value='下架' onclick='upGoods("+result.data[i].id+",\""+result.data[i].isDefault+"\")'></td>";
                    html+="</tr>";
                }
                $("#tb").html(html);
            }
        )
    }

    /** 编辑 */
    function bianji() {
        var check_value = [];
        $("#tb").find('input[name="id"]:checked').each(function() {
            check_value.push($(this).val());
        });
        id = check_value[0];
        if (check_value.length > 1) {
            layer.msg("只能选择一个");
            return;
        }

        if (check_value.length < 1) {
            layer.msg("至少选择一个");
            return;
        }
        layer.open({
            type: 2,
            title: '编辑',
            shadeClose: true,
            move: false,
            shade: 0.8,
            content: "<%=request.getContextPath()%>/productSku/toUpdateSku?id=" + id +"&productId=" + '${product.id}'
        });
    }

    /** 修改库存 */
    function updateCount() {
        var check_value = [];
        $("#fm").find('input[name="id"]:checked').each(function() {
            check_value.push($(this).val());
        });
        id = check_value[0];
        if (check_value.length > 1) {
            layer.msg("只能选择一个");
            return;
        }

        if (check_value.length < 1) {
            layer.msg("至少选择一个");
            return;
        }
        layer.open({
            type: 2,
            title: '库存',
            shadeClose: true,
            move: false,
            shade: 0.8,
            content: "<%=request.getContextPath()%>/productSku/toUpdateSkuCount?id=" + id +"&productId=" + '${product.id}'
        });
    }

    /** 修改默认*/
    function updateDefault() {
        var check_value = [];
        $("#fm").find('input[name="id"]:checked').each(function() {
            check_value.push($(this).val());
        });
        id = check_value[0];
        if (check_value.length > 1) {
            layer.msg("只能选择一个");
            return;
        }

        if (check_value.length < 1) {
            layer.msg("至少选择一个");
            return;
        }
        $.post(
            "<%=request.getContextPath()%>/productSku/updateDefault?id=" + id +"&productId=" + '${product.id}',
            function(result) {
                if (result.code == 200){
                    layer.msg('修改成功，正在返回', {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        location.href = "<%=request.getContextPath()%>/product/toShow";
                    });
                }
                    layer.msg();
                    });
    }

    /** 商品下架 */
    function upGoods(id,isDefault){
        if(isDefault == "ISDEFAULT"){
            layer.msg("此物品是默认选中状态，请先取消默认，后下架");
            return;
        }$.post(
            "<%=request.getContextPath()%>/productSku/downShelf",
            {
                "id":id,
                "skuStatus":'DOWN'
            },
            function (result){
                if (result.code == 200){
                    layer.msg("下架成功");
                    show();
                }
            }
        )
    }

</script>
</html>
