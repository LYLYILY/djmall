<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jq/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="id" value="${address.id}">
    收件人：<input type="text" name="addressee" value="${address.addressee}"><br>
    手机号：<input type="text" id="phone" name="phone" value="${address.phone}" onblur="checkPhone()"><br>
    省市县：<select id="province" name="province" onchange="changeCity()">
    <option value="">--请选择省--</option>
</select>
    <select id="city" name="city" onchange="changeDistrict()">
        <option value="">--请选择市--</option>
    </select>
    <select id="district" name="district" onchange="changeDhiddenValue()">
        <option value="">--请选择县(区)--</option>
    </select><br>
    <input type="hidden" name="userProvince" id="phidden">
    <input type="hidden" name="userCity" id="chidden">
    <input type="hidden" name="userDistrict" id="dhidden">
    街道小区详细地址：<input type="text" value="${address.address}" name="address" ><br>
    <input type="button" value="修改" onclick="upd()">
</form>
</body>
<script>

    $(function(){
        show();
    });

    //展示
    function show(){
        var index = layer.load(0, {shade: 0.3});
        $.get(
            "<%=request.getContextPath()%>/address/showByPid",
            {"pid":0},
            function(result){
                if(result.code!=200){
                    layer.msg(result.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var list=result.data[i];
                    html+="<option value='"+list.id+"'>"+list.areaName+"</option>";
                }
                $("#province").append(html);
                layer.close(index);
            });
    }

    function changeCity(){
        //当省的内容发生变化的时候，响应的改变省的隐藏域的值
        $("#phidden").val($("#province option:selected").html());
        //下拉列表框标签对象的val()方法就是选中的option标签的value的属性值
        var pid = $("#province").val();
        $.ajax({
            url:"<%=request.getContextPath()%>/address/showByPid",
            data:{"pid":pid},
            dataType:"json",
            success:function(result){
                //清空城市下拉列表框的内容
                $("#city").html("<option value=''>-- 请选择市 --</option>");
                $("#district").html("<option value=''>-- 请选择区/县 --</option>");
                //遍历json，json数组中每一个json，都对应一个省的信息，都应该在省的下拉列表框下面添加一个<option>
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var list=result.data[i];
                    html+="<option name='areaName' value='"+list.id+"'>"+list.areaName+"</option>";
                }
                $("#city").append(html);
            }
        });

    }

    function changeDistrict(){
        //当城市的内容发生变化的时候，相应的改变城市的隐藏域的值
        $("#chidden").val($("#city option:selected").html());
        //页面加载完成，将省的信息加载完成
        //下拉列表框标签对象的val()方法就是选中的option标签的value的属性值
        var pid = $("#city").val();
        $.ajax({
            url:"<%=request.getContextPath()%>/address/showByPid",
            data:{"pid":pid},
            dataType:"json",
            success:function(result){
                //清空城市下拉列表框的内容
                $("#district").html("<option value=''>-- 请选择区/县 --</option>");
                var html = "";
                for (var i = 0; i < result.data.length; i++) {
                    var list=result.data[i];
                    html+="<option value='"+list.id+"'>"+list.areaName+"</option>";
                }
                $("#district").append(html);
            }
        });
    }

    function changeDhiddenValue(){
        //当城市的内容发生变化的时候，相应的改变城市的隐藏域的值
        $("#dhidden").val($("#district option:selected").html());
    }

    function upd(){
        $.post(
            "<%=request.getContextPath()%>/receiveAddress/updateAddress",
            $("#fm").serialize(),
            function (result){
                if (result.code == 200){
                    parent.location.href = "<%=request.getContextPath()%>/userToken/toShippingAddress";
                }
                layer.msg(result.msg);
            }
        );

    }

    function checkPhone() {
        var phone = document.getElementById('phone').value;
        if(!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(phone))){
            layer.msg("手机号格式有误，请重填");
            return false;
        }
    }
</script>
</html>

