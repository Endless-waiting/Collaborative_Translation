<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>添加小组</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script type="application/javascript" src="/js/jquery.min.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加小组</legend>
</fieldset>

<form class="layui-form" id="addG">
    <div class="layui-form-item">
        <label class="layui-form-label">小组名称</label>
        <div class="layui-input-inline">
            <input type="text" name="gname" autocomplete="off" placeholder="请输入小组名称" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">小组组长</label>
            <div class="layui-input-inline">
                <select name="leader" lay-verify="required" lay-search="" id="selectLeader">
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">小组成员</label>
        <div class="layui-input-inline">
            <input type="text" name="members" autocomplete="off" placeholder="小组成员" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">翻译书籍</label>
        <div class="layui-input-inline">
            <input type="text" name="book" lay-verify="required" lay-reqtext="翻译书籍是必填项，岂能为空？" placeholder="请输入"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">书籍作者</label>
        <div class="layui-input-inline">
            <input type="text" name="author" autocomplete="off" placeholder="请输入标题" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">出版社</label>
        <div class="layui-input-inline">
            <input type="text" name="publisher" autocomplete="off" placeholder="请输入标题" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
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
                    url: '/Groups/addGroup',
                    dataType: 'text',
                    type: 'post',
                    data: $("#addG").serialize(),
                    success: function (result) {
                        if (result == "success") {
                            layer.msg('已经成功添加', function(){
                                //do something
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
                layer.alert("添加失败");
            }

            return false;
        });
    });
    $(function () {
        $.ajax({
            url: '/Groups/getAllMembers',
            dataType: "json",
            type: 'post',
            success: function (result) {
                $("#selectLeader").append("<option value=''>直接选择或搜索选择</option>");
                $.each(result, function (key, values) {
                    $("#selectLeader").append("<option value=" + values.account + ">" + values.account + "~" + values.mname + "~" + values.institute + "~" + values.department + "~" + values.team + "</option>");
                })
            }
        });
    })

</script>

</body>
</html>