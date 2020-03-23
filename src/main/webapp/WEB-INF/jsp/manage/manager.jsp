<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">后台管理</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="/">返回搜索页</a></li>
            <%--<li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>--%>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <sec:authentication property="name" var="imgAdd"></sec:authentication>
                    <img src="/img/${imgAdd}.jpg" class="layui-nav-img">
                    欢迎您！<sec:authentication property="name"></sec:authentication>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="/logout">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <sec:authorize access="hasAnyRole('ROLE_admin')">
                    <li class="layui-nav-item"><a href="/Groups/group" target="zhanshi">小组管理</a></li>
                    <li class="layui-nav-item"><a href="/Members/members" target="zhanshi">人员管理</a></li>
                    <li class="layui-nav-item"><a href="/Vocabulary/vocabulary" target="zhanshi">词汇管理</a></li>
                    <li class="layui-nav-item"><a href="/Set/set" target="zhanshi">设置</a></li>
                    <li class="layui-nav-item"><a href="/Vocabulary/initElasticSearch" target="zhanshi">词汇初始化</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <iframe src="/show.html" frameborder="0" name="zhanshi" id="zhanshi" style="width: 100%; height: 590px;padding-top: 20px;padding-left: 20px"></iframe>
    </div>


    <div class="layui-footer">
        <!-- 底部固定区域 -->
        <center>
            © Collaborative translation - 协同翻译
        </center>

    </div>
</div>
<script src="/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });


</script>
</body>
</html>
