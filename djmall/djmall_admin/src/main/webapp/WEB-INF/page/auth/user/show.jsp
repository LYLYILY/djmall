<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layer/layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <title>展示</title>
</head>

<body >
<form id="fm">
    模糊匹配：<input type="text" placeholder="用户名/手机号/邮箱" name="queryNameOrPhoneOrEmail" ><br>
    级别：
    <c:forEach var="r" items="${role}">
        <input type="radio" name="queryRank" onclick="clean()" value="${r.id}">${r.roleName}
    </c:forEach><br>

    性别：
    <c:forEach var="s" items="${sexList}">
        <input type="radio" name="userSex" value="${s.code}">${s.dictName}
    </c:forEach><br>
    状态：<select name="userStatus">
    <option value="">请选择</option>
    <c:forEach var="u" items="${statusList}">
        <option value="${u.code}">${u.dictName}</option>
    </c:forEach>
</select><br>
        <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="查询" onclick="query()"><br>
        <br><input type="button"  class="layui-btn layui-btn-normal layui-btn-sm" value="修改" onclick="upd()">&nbsp&nbsp
        <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="激活" onclick="att()">&nbsp&nbsp
        <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="重置密码" onclick="newPwd()">&nbsp&nbsp
        <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="删除" onclick="del()">&nbsp&nbsp
        <input type="button" class="layui-btn layui-btn-normal layui-btn-sm" value="授权" onclick="uth()">
    <table  class="layui-table">
        <tr>
            <th>用户id</th>
            <th>用户名</th>
            <th>昵称</th>
            <th>手机号</th>
            <th>邮箱</th>
            <th>性别</th>
            <th>级别</th>
            <th>状态</th>
            <th>注册时间</th>
            <th>最后登陆时间</th>
        </tr>
        <tbody id="tb"></tbody>
    </table><br/>
    <div id="page"></div>
</form>
</body>
<script type="text/javascript">
    $(
        function(){
            show();
        }
    );

    //展示
    function show(){
        var index = layer.load(0, {shade: 0.3});
        $.post(
            "<%=request.getContextPath()%>/user/show",
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
                    html+="<td><input type='hidden' id='"+list.id+"' value='"+list.userStatus+"'/><input name='id_check' type='checkbox' value='"+list.id+"'>"+list.id+"</td>";
                    html+="<td>"+list.userName+"</td>";
                    html+="<td>"+list.nikeName+"</td>";
                    html+="<td>"+list.userPhone+"</td>";
                    html+="<td>"+list.userEmail+"</td>";
                    html+="<td>"+list.userSexShow+"</td>";
                    if (null == list.userRank || '' == list.userRank){
                        html+="<td>无</td>";
                    }else {
                        html+="<td>"+list.userRank+"</td>";
                    }
                    html+="<td>"+list.userStatusShow+"</td>";
                    html+="<td>"+list.createTime+"</td>";
                    html+="<td>"+list.lastLoginTime+"</td>";
                    /*if ( null == list.lastLoginTime){
                        html+="<td>未曾登录</td>";
                    }else{
                        html+="<td>"+list.lastLoginTime+"</td>";
                    }*/
                    html+="</tr>";
                }
                $("#tb").html(html);
                layer.close(index);
            });
    }

    function query(){
        show();
    }

    function uth(){
        var ids=[];
        $("input[name='id_check']:checked").each(function(){
            ids.push(this.value);
        })
        if(ids.length<1){
            layer.msg("至少勾选一项");
            return;
        }
        if(ids.length>1){
            layer.msg("只能勾选一项");
            return;
        }
        layer.open({
            type: 2,
            area: ['250px', '400px'],
            content: ['<%=request.getContextPath()%>/user/toShowUth/'+ ids[0], 'no']
        });
    }

    function upd(){
        var userId = $('input[name="id_check"]:checked').val();
        var ids=[];
        $("input[name='id_check']:checked").each(function(){
            ids.push(userId);
        })
        if(ids.length>1){
            layer.msg("只能勾选一项");
            return;
        }
        if(ids.length<1){
            layer.msg("请选择您要修改的内容");
            return;
        }
        layer.open({
            type: 2,
            area: ['500px', '400px'],
            content: ['<%=request.getContextPath()%>/user/toUpd/'+ ids[0], 'no']
        });
    }

</script>
</html>
