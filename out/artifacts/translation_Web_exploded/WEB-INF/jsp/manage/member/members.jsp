<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>人员管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <script src="/layui/layui.js" charset="utf-8"></script>
    <script type="application/javascript" src="/js/jquery.min.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
</head>
<body>


<div class="layui-btn-group demoTable" id="layerDemo">
    <button data-method="offset" data-type="auto" class="layui-btn layui-btn-normal">添加人员</button>
</div>
<div class="demoTable">
    搜索人员编号ID：
    <div class="layui-inline">
        <input class="layui-input" name="id" id="demoReload" autocomplete="off">
    </div>
    <button class="layui-btn" data-type="reload">搜索</button>
</div>
<table class="layui-table" id="LAY_table_member" lay-filter="user"></table>
<script>
    layui.use('table', function () {
        var table = layui.table;

        //方法级渲染
        table.render({
            elem: '#LAY_table_member'
            , url: '/Members/getAllMembers'
            , cols: [[
                {  field: 'account', title: '账号', sort: true, fixed: true}
                , {field: 'mname', title: '姓名',edit: 'text'}
                , {field: 'institute', title: '单位',edit: 'text'}
                , {field: 'department', title: '科室',edit: 'text'}
                , {field: 'team', title: '项目组',edit: 'text'}
                , {field: 'titles', title: '身份',edit: 'text'}
                , {field: 'gid', title: '翻译小组',edit: 'text'}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
            ]]
            , id: 'testReload'
            , page: true
            , height: 'full-100',
        });

        //监听单元格编辑
        table.on('edit(user)', function (obj) {
            var value = obj.value //得到修改后的值
                , data = obj.data //得到所在行所有键值
                , field = obj.field;//得到字段

            $.ajax({
                url: '/Members/putMemberById',
                dataType: "json",
                type: 'post',
                data: 'mid=' + data.mid + '&' + field + '=' + value,
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

        var $ = layui.$, active = {
            reload: function () {
                var demoReload = $('#demoReload');

                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {id: demoReload.val()}
                }, 'data');
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //监听行工具事件
        table.on('tool(user)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除行么？', function (index) {
                    obj.del();
                    $.ajax({
                        url: '/Members/deleteMemberById',
                        dataType: "json",
                        type: 'post',
                        data: 'mid=' + data.mid,
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
                        },
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
                    area: ['460px', '570px'],
                    maxmin: true,
                    content: '/Members/PostMemberPage'
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