<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>

</head>

<body>
<form id="fm">
    <center>
    <input type="hidden" name="id" value="${skuDTO.id}">
    SKU属性<input type="text" name="skuName" value="${skuDTO.skuName}" readonly><br>
    库存<input type="text" name="skuCount" value="${skuDTO.skuCount}"><br>
    价格<input type="text" name="skuPrice" value="${skuDTO.skuPrice}"><br>
    折扣<input type="text" name="skuRate" value="${skuDTO.skuRate}"><br>
    <input type="button" value="确定" onclick="update()"><br>
    </center>
</form>

</body>
<script type="text/javascript">

    function update(){
        $.post(
            "${ctx}/productSku/updateSku",
            $("#fm").serialize(),
            function (result){
                var index = layer.load();
                var i = result.code == 200 ? 6 : 5;
                layer.msg(result.msg, {
                        icon: i,
                        time: 2000,//2秒关闭（如果不配置，默认是3秒）
                        shade: [0.8, '#1E9FFF']//遮罩
                    }, function () {
                        layer.close(index);
                        if (result.code == 200) {
                            parent.location.href="<%=request.getContextPath()%>/product/toUpdate/" + '${productId}';
                        }
                    }
                )
            })
    }

</script>
</html>