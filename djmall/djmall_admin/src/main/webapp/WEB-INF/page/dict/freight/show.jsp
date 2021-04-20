<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <title>运费展示</title>
</head>
<body>
<form id="fm">
    <table>
        <tr>
            <td>物流公司</td>
            <td>
                <select name="logisticsCompany">
                    <c:forEach items="${logisticsCompanyList}" var="l">
                        <option value=${l.code}>${l.dictName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>运费</td>
            <td><input type="text" name="freight"  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,1})?).*$/g, '$1')"></td>
        </tr>
    </table>
    <shiro:hasPermission name="FREIGHT_ADD_BTN">
        <input type="button" value="新增" onclick="add()">
    </shiro:hasPermission>
</form>

    <table border="1px solid">
        <tr>
            <th>物流公司</th>
            <th>运费</th>
            <th>操作</th>
        </tr>
        <tbody id="tb"></tbody>
    </table>

</body>
<script type="text/javascript">
    $(function(){
        show();
    })

    /**展示 */
    function show(){
        $.post(
            "<%=request.getContextPath()%>/freight/show",
            $("#fm").serialize(),
            function(result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var list=result.data[i];
                    html+="<tr>";
                    html+="<td>"+list.logisticsCompany+"</td>";
                    if(list.freight == 0){
                        html+="<td>包邮</td>";
                    }else {
                        html+="<td>￥"+list.freight+"</td>";
                    }
                    html+="<td><shiro:hasPermission name="FREIGHT_UPDATE_BTN"><input type='button' value='修改' onclick='toUpdate("+list.id+")'></shiro:hasPermission>";
                    html+="</tr>";
                }
                $("#tb").html(html);
            });
    }

    /** 新增运费 */
    function add(){
        var index = layer.load();
        $.post(
            "<%=request.getContextPath()%>/freight/add",
            $("#fm").serialize(),
            function (result){
                layer.close(index);
                if(result.code == 200){
                    layer.msg("新增成功");
                    show();
                    return;
                }
                layer.msg(result.msg);
            }
        );
    }

    /** 修改 */
    function toUpdate(id){
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: false,
            shade: 0.5,
            area: ['300px', '150px'],
            content:"<%=request.getContextPath()%>/freight/toUpdate?id="+id
        });
    }
</script>
</html>
