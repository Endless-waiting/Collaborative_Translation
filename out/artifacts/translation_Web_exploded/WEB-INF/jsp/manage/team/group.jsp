<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>小组管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <script src="/layui/layui.js" charset="utf-8"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
</head>
<body>


<div class="layui-btn-group demoTable" id="layerDemo">
    <button data-method="offset" data-type="auto" class="layui-btn layui-btn-normal">添加小组</button>
</div>
<table class="layui-table" id="LAY_table_group" lay-filter="user"></table>

<script>
    layui.use('table', function () {
        var table = layui.table;

        //方法级渲染
        table.render({
            elem: '#LAY_table_group'
            , url: '/Groups/getGroup'
            , cols: [[
                {field: 'gid', title: '小组编号', sort: true, fixed: true}
                , {field: 'gname', title: '小组名称', edit: 'text'}
                , {field: 'leader', title: '组长', edit: 'text'}
                , {field: 'members', title: '成员', edit: 'text'}
                , {field: 'book', title: '翻译书籍', edit: 'text'}
                , {field: 'author', title: '作者', edit: 'text'}
                , {field: 'publisher', title: '出版社', edit: 'text'}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
            ]]
            , id: 'testReload'
            , page: true
            , height: 'full-100'
        });

        var $ = layui.$;

        //监听单元格编辑
        table.on('edit(user)', function (obj) {
            var value = obj.value //得到修改后的值
                , data = obj.data //得到所在行所有键值
                , field = obj.field;//得到字段

            $.ajax({
                url: '/Groups/putGroupById',
                dataType: "json",
                type: 'post',
                data: 'gid=' + data.mid + '&' + field + '=' + value,
                success: function (data) {
                    var result = data;
                    alert(data);
                    if (data == 'error') {
                        layer.msg("修改失败");//失败的表情
                        return;
                    } else if (data == 'success') {
                        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value + '修改成功');
                    }
                },
                complete: function () {
                    layer.close(this.layerIndex);
                },
            });
        });

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //监听行工具事件
        table.on('tool(user)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('十分确定要删除吗?', function (index) {
                    var data = obj.data;
                    obj.del();
                    $.ajax({
                        url: '/Groups/deleteGroupById',
                        dataType: "text",
                        type: 'post',
                        data: 'gid=' + data.gid,
                        success: function (data) {
                            var result = data;
                            alert(data);
                            if (data == 'error') {
                                layer.msg("修改失败");//失败的表情
                                return;
                            } else if (data == 'success') {
                                layer.msg('成功删除');
                            }
                        },
                        complete: function () {
                            layer.close(index);

                        }
                    });

                });
            }
        });
    });
    layui.use('table', function () {
        var table = layui.table;

    });

    layui.use('layer', function(){ //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

        //触发事件
        var active = {
            offset: function(othis){
                //iframe窗
                layer.open({
                    type: 2,
                    shadeClose: true,
                    shade: false,
                    area: ['460px', '550px'],
                    maxmin: true,
                    content: '/Groups/addGroupPage'
                });
            }
        };

        $('#layerDemo .layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });

    });
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

</body>
</html>