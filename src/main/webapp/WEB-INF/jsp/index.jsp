<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>协同翻译</title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script type="application/javascript" src="/js/jquery.min.js"></script>
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
        <a href="/Vocabulary/addVocabulary" name="tj_trhao123" class="mnav">添加单词</a>
    </sec:authorize>
        <sec:authorize access="hasRole('ROLE_admin')">
            <a href="/manage" name="manage" class="mnav">后台管理</a>
        </sec:authorize>
    <div class="bdnuarrow bdbriarrow" style="display: none;"></div>
</div>
<div class="container">
    <div class="logo" style="background-image: url(/img/iie.png)"></div>

    <div class="input">
        <input type="text" placeholder="请输入要搜索的内容" autocomplete="off" id="search-input">
        <table id="search_table" style="display: none;width: 100%" ></table>
    </div>
    <div class="search"></div>

</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
    $(function () {
        helangSearch.init();

        $('#search-input').bind('input propertychange', function() {
            var val = $("#search-input").val();
            if(val=="" || val.length <1){
                $("#search_div").css("display","none");
                $("#search_table").css("display","none");
            }else {
                $.ajax({
                    url: '/Vocabulary/getListOfVocabulary',
                    dataType: "json",
                    type: 'post',
                    data:'string='+ val,
                    success: function (data) {
                        $("#search_table").css("display","block");
                        $("#search_table").empty();
                        $.each(data, function (key, values) {
                            $("#search_table").append("<tr><td width='35px'>"+values.abbreviation+"</td><td width='125px' colspan='5px'>"+ values.word + "</td><td width='200px'>"+values.translation+"</td><td width='75px'>"+values.mname+ "</td><td width='75px'>"+values.titles+"</td></tr>");
                        })
                    }
                });
            }

        });

        $("#loginout").click(function () {
            var confi = confirm("是否退出");
            if (confi) {
                $.post("/logout");
            }
        })
    })
</script>
</body>
</html>