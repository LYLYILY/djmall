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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/jquery.ztree.all.min.js"></script>
<body>
<form id="fm">
    <shiro:hasPermission name="SAVE_REL_ATTR_BTN">
        <input type="button"  value="保存" onclick="addsku()"/>
    </shiro:hasPermission>
</form>
<table border="1px solid" cellspacing="0px" align="center">
    <tr>
        <th>编号</th>
        <th>属性名</th>
        <th>属性值</th>
    </tr>
    <tbody id="tb"></tbody>
</table>
</body>
<script type="text/javascript">

    $(function (){
        list();
    });
    function list() {
        $.post(
            "<%=request.getContextPath()%>/sku/showRelevance",
            function (result) {
                var i = result.code == 200 ? 6 : 5;
                    if (result.code == 200) {
                        var html = "";
                        for (var i = 0; i < result.data.length; i++) {
                            var y = result.data[i];
                            html += "<tr>";
                            html+="<td>";
                            html+="<input type='checkbox' name='id_check' value='"+y.id+"'/>"+y.id+"";
                            html+="</td>";
                            html += "<td>" + y.attrName + "</td>";
                            html += "<td>" + y.attrValue + "</td>";
                        }
                        $("#tb").html(html);
                    }
                }
            )
    }
    function addsku(){
        var ids=[];
        $("input[name='id_check']:checked").each(function(){
            ids.push(this.value);
        })
        if(ids.length<1){
            layer.msg("至少勾选一项");
            return;
        }
       $.post(
           "<%=request.getContextPath()%>/sku/add",
           {"ids[]":ids, "productType": '${productType}'},
           function (result){
               if (result.code == 200){
                   parent.location.href = "<%=request.getContextPath()%>/sku/toShow";
               }
               layer.msg(result.msg);
           }
       );
    }
</script>
</html>