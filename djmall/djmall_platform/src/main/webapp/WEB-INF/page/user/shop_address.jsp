<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>
</head>
<body>
<form id="fm">
    <input type="button" value="新增收货地址" onclick="add()">
    <table border="1px solid">
        <tr>
            <th>编号</th>
            <th>收货人</th>
            <th>详细地址</th>
            <th>手机号</th>
            <th>操作</th>
        </tr>
        <tbody id="tb"></tbody>
    </table>
</form>
</body>
<script>

    $(function(){
        show();
    });

    //展示
    function show(){
        var index = layer.load(0, {shade: 0.3});
        $.post(
            "<%=request.getContextPath()%>/receiveAddress/showReceiveAddress",
            {"userId": Cookies.get("USER_ID")},
            function(result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var list=result.data[i];
                    html+="<tr>";
                    html+="<td>"+list.id+"</td>";
                    html+="<td>"+list.addressee+"</td>";
                    html+="<td>"+list.userProvince+"-"+list.userCity+"-"+list.userDistrict+"-"+list.address+"</td>";
                    html+="<td>"+list.phone+"</td>";
                    html+="<td><a onclick='update("+list.id+")' style='color: #1E9FFF'>修改</a> <a onclick='del("+list.id+")' style='color: #1E9FFF'>删除</a></td>";
                    html+="</tr>"
                }
                $("#tb").html(html);
                layer.close(index);
            });
    }

    function add(){
        layer.open({
            type: 2,
            title: '添加页面',
            shadeClose: false,
            shade: 0.5,
            area: ['500px', '400px'],
            content:"<%=request.getContextPath()%>/receiveAddress/toAddsAddress"
        });
    }

    function update(id){
        layer.open({
            type: 2,
            title: '添加页面',
            shadeClose: false,
            shade: 0.5,
            area: ['300px', '400px'],
            content:"<%=request.getContextPath()%>/receiveAddress/toUpdateAddress/" +id
        });
    }

    function del(id){
        $.post(
            "<%=request.getContextPath()%>/receiveAddress/delAddress",
            {"id": id},
            function (result){
                if (result.code == 200){
                    location.href = "<%=request.getContextPath()%>/userToken/toShippingAddress";
                }
            }
        );
    }
</script>
</html>

