<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>添加人员</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script type="application/javascript" src="/js/jquery.min.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加人员</legend>
</fieldset>

<form class="layui-form" id="addM">
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="mname" autocomplete="off" placeholder="请输入姓名" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline">
            <input type="password" name="password" autocomplete="off" placeholder="请输入密码" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">单位</label>
            <div class="layui-input-inline">
                <select name="institute" lay-verify="required" lay-search="" id="selectInstitute">
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">科室/部门</label>
            <div class="layui-input-inline">
                <select name="department" lay-verify="required" lay-search="" id="selectDepartment">
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">项目组</label>
            <div class="layui-input-inline">
                <select name="team" lay-verify="required" lay-search="" id="selectTeam">
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">身份</label>
            <div class="layui-input-inline">
                <select name="titles" lay-verify="required" lay-search="" id="selectTitles">
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">翻译小组</label>
            <div class="layui-input-inline">
                <select name="gid" lay-search="" id="selectGroup">
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script src="/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#date'
        });

        laydate.render({
            elem: '#date1'
        });

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');

        //自定义验证规则
        form.verify({
            content: function (value) {
                layedit.sync(editIndex);
            }
        });

        //监听提交
        form.on('submit(demo1)', function (data) {
            var b = confirm("是否要提交");
            if (b) {
                $.ajax({
                    url: '/Members/postMember',
                    dataType: "text",
                    type: 'post',
                    data: $("#addM").serialize(),
                    success: function (result) {
                        if (result == "success") {
                            layer.msg('已经成功添加', function(){
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.location.reload();//刷新父页面
                                parent.layer.close(index); //再执行关闭
                            });
                        } else {
                            layer.alert("添加失败");
                        }
                    }
                });
            } else {
                layer.alert("提交失败");
            }
            return false;
        });
    });
    $(function () {
        $.ajax({
            url: '/Members/getAllInstitute',
            dataType: "json",
            type: 'post',
            success: function (result) {
                $("#selectInstitute").append("<option value=''>直接选择或搜索选择</option>");
                $.each(result, function (key, values) {
                    $("#selectInstitute").append("<option>" + values + "</option>");
                })
            }
        });
        $.ajax({
            url: '/Members/getAllDepartment',
            dataType: "json",
            type: 'post',
            success: function (result) {
                $("#selectDepartment").append("<option value=''>直接选择或搜索选择</option>");
                $.each(result, function (key, values) {
                    $("#selectDepartment").append("<option>" + values + "</option>");
                })
            }
        });
        $.ajax({
            url: '/Members/getAllTeam',
            dataType: "json",
            type: 'post',
            success: function (result) {
                $("#selectTeam").append("<option value=''>直接选择或搜索选择</option>");
                $.each(result, function (key, values) {
                    $("#selectTeam").append("<option>" + values + "</option>");
                })
            }
        });
        $.ajax({
            url: '/Members/getAllTitle',
            dataType: "json",
            type: 'post',
            success: function (result) {
                $("#selectTitles").append("<option value=''>直接选择或搜索选择</option>");
                $.each(result, function (key, values) {
                    $("#selectTitles").append("<option>" + values + "</option>");
                })
            }
        });
        $.ajax({
            url: '/Members/getAllGroup',
            dataType: "json",
            type: 'post',
            success: function (result) {
                $("#selectGroup").append("<option value=''>直接选择或搜索选择</option>");
                $.each(result, function (key, values) {
                    $("#selectGroup").append("<option value='"+values.gid+"'>" + values.gname+"~"+ values.book + "</option>");
                })
            }
        });
    })

</script>

</body>
</html>
