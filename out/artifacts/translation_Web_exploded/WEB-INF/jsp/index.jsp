<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>中科院信工所 协同翻译</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
<div id="u1" style="float: right">
    <%--注册按钮--%>
    <%--<sec:authorize access="!isAuthenticated()">
        <a href="/register" name="register" class="mnav">注册</a>
    </sec:authorize>--%>
        <sec:authentication property="name"></sec:authentication>
    <sec:authorize access="!isAuthenticated()">
         <a href="/login" name="login_in" class="mnav">登录</a>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <a href="javascript:;" name="login_out" id="loginout" class="mnav">注销</a>
        <a href="javascript:;" name="tj_trhao123" class="mnav">添加单词</a>
    </sec:authorize>
        <sec:authorize access="hasRole('ROLE_admin')">
            <a href="/manage" name="tj_trnews" class="mnav">后台管理</a>
        </sec:authorize>

    <div class="bdnuarrow bdbriarrow" style="display: none;"></div>
</div>
<div class="container">
    <div class="logo" style="background-image: url(/img/iie.png)"></div>
    <div class="input">
        <input type="text" placeholder="请输入要搜索的内容" autocomplete="off" id="search-input">
        <div class="hot-list"></div>
    </div>
    <div class="search"></div>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
    $(function () {
        helangSearch.init();

        $("#loginout").click(function () {
            var confi = confirm("是否退出");
            if (confi) {
                $.post("/login");
            }
        })
    })
</script>
</body>
</html>