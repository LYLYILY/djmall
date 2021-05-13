<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript">

        /*商品数量+1*/
        function numAdd(){
            var num_add = parseInt($("#skuCount").val())+1;
            if($("#skuCount").val()==""){
                num_add = 1;
            }
            $("#skuCount").val(num_add);
        }

        /*商品数量-1*/
        function numDec(){
            var num_dec = parseInt($("#skuCount").val())-1;
            if(num_dec<1){
                //购买数量必须大于或等于1
                alert("购买数量必须大于或等于1");
            }else{
                $("#skuCount").val(num_dec);
            }
        }

        function update(){
            $.post(
                "<%=request.getContextPath()%>/productSku/updateSkuCount",
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
</head>
<body>
<form id="fm">
<input type="hidden" value="${id}" name="id">
<input type="button" value="-" onclick="numDec()">
<input type="text" id="skuCount" name="skuCount" value="${count}"/>
<input type="button" value="+" onclick="numAdd()"><br>
<input type="button" value="确定" onclick="update()">
</form>
</body>
</html>
