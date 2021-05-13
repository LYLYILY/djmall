<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script><body>
<table border="1px solid" cellspacing="0px" align="center">
    <tr>
        <th>编号</th>
        <th>商品类型</th>
        <th>属性名</th>
        <th>操作</th>
    </tr>
    <tbody id="tb"></tbody>
</table>
</body>
<script type="text/javascript">

    $(function (){
        show();
    });

    function show() {
        $.post(
            "<%=request.getContextPath()%>/sku/show",
            function (result) {
                var i = result.code == 200 ? 6 : 5;
                    if (result.code == 200) {
                        var html = "";
                        for (var i = 0; i < result.data.length; i++) {
                            var y = result.data[i];
                            html += "<tr>";
                            html += "<td>"+y.id+"</td>";
                            html += "<td>"+y.productName+"</td>";
                            html += "<td>"+y.attrName+"</td>";
                            html +=
                                "<td>" +
                                "<shiro:hasPermission name="COMMON_SKU_REL_ATTR_BTN"><a href='#' onclick='addSku(&quot;"+ y.productType + "&quot;)'>关联资源</a></shiro:hasPermission>" +
                                "</td>";

                            html += "</tr>"
                        }
                        $("#tb").html(html);
                    }
                }
            )
    }
    function addSku(productType){
        var index = layer.load();
        layer.open({
            type: 2,
            anim: 4,
            area: ['400px', '260px'],
            content: "<%=request.getContextPath()%>/sku/toAdd/" + productType
        })
        layer.close(index);
    }
</script>
</html>
