<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>商品sku展示</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/token/js.cookie.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
</head>
<body>
<div style="float: right">
<a href="javascript:void(0)" style="color: #00B83F ">首页</a>&nbsp&nbsp<a id="login" onclick="login()" target="_blank" style="color: #00B83F ">登录</a><a id="logins"></a>&nbsp&nbsp<a href="javascript:void(0)" onclick="toAdd()" style="color: #00B83F "/>注册</a>&nbsp&nbsp<a href="<%=request.getContextPath()%>/shop/toShopCar" style="color: #00B83F ">我的购物车</a>
</div><br><br>
<form id="fm">
    <input type="hidden" id="userId" name="userId">
    <div style="text-align:center;vertical-align:middle;">
        <input style="width: 600px; height: 30px" type="text" name="productName"><br><br>
    </div>
价格：<input type="text" name="skuPriceMin">--<input type="text" name="skuPriceMax"><br><br>
分类：<c:forEach items="${productList}" var="p">
         <input type="checkbox" name="productTypeList" value="${p.code}">${p.dictName}
     </c:forEach><br><br>
</form>
<input type="button" value="查询" onclick="query()">
<table class="layui-table">
    <tr>
        <th>名称</th>
        <th>价格</th>
        <th>库存</th>
        <th>分类</th>
        <th>折扣</th>
        <th>邮费</th>
        <th>图片</th>
        <th>描述</th>
        <th>点赞量</th>
    </tr>
    <tbody id="tb"/>
</table>
<div id="pageDiv"/>
</body>
<script type="text/javascript">
    var userId = Cookies.get("USER_ID");
    $("#userId").val(userId);
    var pageNo = 1;

    $(function(){
        show(pageNo);
        if (check_login()){
            $("#login").hide();
            $("#logins").html("<a href='javascript:void(0)' onclick='logins()' target='_blank'>"+Cookies.get("NICK_NAME")+"</a>");
        }
    })

    function login(){
            layer.open({
                type: 2,
                title: '登陆页面',
                shadeClose: false,
                shade: 0.5,
                area: ['300px', '400px'],
                content:"<%=request.getContextPath()%>/userToken/toLoginToken"
            });
    }

    function logins(){
       location.href = "<%=request.getContextPath()%>/index/toIndex";
    }

    /** 展示*/
    function show(pageNo){
        $.post(
            "<%=request.getContextPath()%>/product/show/"+pageNo,
            $("#fm").serialize(),
            function(result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                var pageHtml = "";
                for (var i = 0; i < result.data.records.length; i++) {
                    var e =result.data.records[i];
                    html+="<tr>";
                    html+="<td><a href='<%=request.getContextPath()%>/product/toShowDetail?productId="+e.productId+"'>"+e.productName+"</a></td>";
                    html+="<td>"+e.skuPrice+"</td>";
                    html+="<td>"+e.skuCount+"</td>";
                    html+="<td>"+e.productTypeShow+"</td>";
                    html+="<td>"+e.skuRate+"</td>";
                    if(e.freight == 0){
                        html+="<td>包邮</td>";
                    }else {
                        html+="<td>平邮，"+e.freight+"</td>";
                    }
                    html+="<td><img src='"+e.productImg+"'></td>";
                    html+="<td>"+e.productDisc+"</td>";
                    html+="<td>" +e.likeNum+ "</td>";
                    html+="</tr>";
                }
                $("#tb").html(html);
                pageHtml += "<td><input type='button' value='上一页' onclick='page(true,null)'></td>"
                pageHtml += "<td><input type='button' value='下一页' onclick='page(false,"+result.data.pages+")'></td>"
                $("#pageDiv").html(pageHtml);
            });
    }

    /** 分页*/
    function page(isUp,pages) {
        if(isUp){
            if(pageNo <= 1) {
                layer.msg("首页" ,{icon : 0});
                return;
            }
            pageNo--;
        }else {
            if(pageNo >= pages ){
                layer.msg("尾页" ,{icon : 0});
                return;
            }
            pageNo++;
        }
        layer.msg(pageNo ,{icon : 1});
        show(pageNo);
    }

    /** 模糊查*/
    function query(){
        show(pageNo);
    }

    function toAdd(){
        location.href = "<%=request.getContextPath()%>/userToken/toAdd";
    }

    //判断当前窗口路径与加载路径是否一致。
    if(window.top.document.URL != document.URL){
        //将窗口路径与加载路径同步
        window.top.location = document.URL;
    }

</script>
</html>
