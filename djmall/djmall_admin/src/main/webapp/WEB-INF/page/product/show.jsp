<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<body>
<form id="fm">
    名称：<input type="text" name="productName" id="productName"><br>
    分类：<c:forEach items="${productList}" var="p">
    ${p.dictName}<input type="checkbox" name="productTypeList" value="${p.code}" id="productType">
        </c:forEach><br><br>
</form>
<shiro:hasPermission name="PRODUCT_SEARCH_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="搜索" onclick="query()"><br><br>
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_ADDINDEXES_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="增量索引">
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_RESTARTINDEXES_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="重构索引"><br><br>
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_INSERT_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="新增" onclick="toAdd()">
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_UPDATE_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="修改" onclick="toUpdate()">
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_UPDOWN_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="上架/下架" onclick="upOrDown()">
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_COMMENT_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="查看评论">
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_DOWNLOADTEMP_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="下载导入模板">
</shiro:hasPermission>
<shiro:hasPermission name="PRODUCT_LEAD_BTN">
    <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="导入"><br><br>
</shiro:hasPermission>
<table class="layui-table">
    <tr>
        <th></th>
        <th>名称</th>
        <th>类型</th>
        <th>状态</th>
        <th>邮费</th>
        <th>商品图片</th>
        <th>描述</th>
        <th>点赞量</th>
        <th>订单量</th>
    </tr>
    <tbody id="tb"/>
</table>
<div id="pageDiv"></div>
</body>
<script type="text/javascript">
    var pageNo = 1;
    $(function(){
        show(pageNo);
    })

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
                    html+="<td><input type='hidden' id='"+e.id+"' value='"+e.productStatus+"'/><input name='id_check' type='checkbox' value='"+e.id+"'>"+e.id+"</td>";
                    html+="<td>"+e.productName+"</td>";
                    html+="<td>"+e.productTypeShow+"</td>";
                    html+="<td>"+e.productStatusShow+"</td>";
                    if(e.freight == 0){
                        html+="<td>包邮</td>";
                    }else {
                        html+="<td>￥"+e.freight+"</td>";
                    }
                    html+="<td><img src='"+e.productImg+"'></td>";
                    html+="<td>"+e.productDisc+"</td>";
                    html+="<td>"+e.likeNum+"</td>";
                    html+="<td>"+e.orderNum+"</td>";
                    html+="</tr>";
                }
                $("#tb").html(html);
                pageHtml += "<td><input type='button' value='上一页' onclick='page(true,null)'></td>"
                pageHtml += "<td><input type='button' value='下一页' onclick='page(false,"+result.data.pages+")'></td>"
                $("#pageDiv").html(pageHtml);
            });
    }

    /** 分页*/
    function page(isUp, pages) {
        if(isUp){
            if(pageNo <= 1) {
                layer.msg("首页" ,{icon : 0});
                return;
            }
            pageNo--;
        }else {
            if(pageNo >= pages){
                layer.msg("尾页" ,{icon : 0});
                return;
            }
            pageNo++;
        }
        layer.msg(pageNo, {icon : 1});
        show(pageNo);
    }
    /** 去添加*/
    function toAdd() {
        location.href = "<%=request.getContextPath()%>/product/toAdd"
    }

    /**上下架*/
    function upOrDown() {
        var productId = $('input[name="id_check"]:checked').val();
        var ids=[];
        $("input[name='id_check']:checked").each(function(){
            ids.push(productId);
        })
        if(ids.length>1){
            layer.msg("只能勾选一项");
            return;
        }
        if(ids.length<1){
            layer.msg("请选择您要修改的内容");
            return;
        }
        var statusOld = $("#" + ids[0]).val();
        var statusNew = statusOld == "UP" ? "DOWN" : "UP";
        var statusMsg = statusOld == "UP" ? "下架" : "上架";
        layer.confirm(
            '您确定要将该商品置为'+statusMsg+'吗?',
            {icon: 3,
                title:'提示',
                btn:['确定','再考虑一下']},
            function(){
                $.post(
                    "<%=request.getContextPath()%>/product/updateStatus",
                    {"id":ids[0], "productStatus":statusNew},
                    function(result){
                        if (result.code == 200){
                            location.href="<%=request.getContextPath()%>/product/toShow"
                            return;
                        }
                        layer.msg("操作失败");
                    }
                );
            });
    }

    /** 去修改*/
    function toUpdate(){
        var productId = $('input[name="id_check"]:checked').val();
        var ids=[];
        $("input[name='id_check']:checked").each(function(){
            ids.push(productId);
        })
        if(ids.length>1){
            layer.msg("只能勾选一项");
            return;
        }
        if(ids.length<1){
            layer.msg("请选择您要修改的内容");
            return;
        }
        location.href = "<%=request.getContextPath()%>/product/toUpdate/"+ids[0]
    }

    /**模糊查*/
    function query() {
        show(pageNo);
    }
</script>
</html>
