<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>词汇管理</title>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <style>
        body{margin: 10px;}
        .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    </style>
    <script type="application/javascript" src="/js/jquery.min.js"></script>
</head>
<body>

<table class="layui-hide" id="demo" lay-filter="vocabulary"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="/layui/layui.js"></script>
<script>
    layui.use(['laydate', 'laypage', 'layer', 'table',  'upload', 'element', 'slider'], function(){
        var laypage = layui.laypage //分页
            ,layer = layui.layer //弹层
            ,table = layui.table //表格
            ,upload = layui.upload //上传
            ,element = layui.element //元素操作
            ,slider = layui.slider //滑块

        //执行一个 table 实例
        table.render({
            elem: '#demo'
            ,height: 'full-80'
            ,url: '/Vocabulary/getVocabulary' //数据接口
            ,title: '词汇表'
            ,page: true //开启分页
            ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            ,totalRow: true //开启合计行
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'vid', title: '单词编号', width:80, sort: true, fixed: 'left'}
                ,{field: 'abbreviation', title: '缩写',edit: 'text'}
                ,{field: 'word', title: '单词', sort: true,edit: 'text'}
                ,{field: 'translation', title: '翻译',edit: 'text'}
                ,{field: 'mname', title: '翻译人',edit: 'text'}
                ,{field: 'titles', title: '职称', width:150}
                ,{field: 'gid', title: '所属翻译小组', width: 200}
                ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
            ]]
        });

        //监听头工具栏事件
        table.on('toolbar(vocabulary)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    //iframe窗
                    layer.open({
                        type: 2,
                        shadeClose: true,
                        shade: false,
                        area: ['460px', '490px'],
                        maxmin: true,
                        content: '/Vocabulary/addVocabulary'
                    });
                    break;
                case 'delete':
                    if(data.length === 0){
                        layer.msg('请选择一行');
                    } else {
                        layer.confirm('真的删除行么', function(index){
                            obj.del(); //删除对应行（tr）的DOM结构
                            $.ajax({
                                url: '/Vocabulary/deleteVocabularyById',
                                dataType: "text",
                                type: 'post',
                                data: 'vid=' + data.vid,
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
                            layer.close(index);
                        });
                    }
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(vocabulary)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构
                    $.ajax({
                        url: '/Vocabulary/deleteVocabularyById',
                        dataType: "text",
                        type: 'post',
                        data: 'vid=' + data.vid,
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
                    layer.close(index);
                });
            }
        });

        //监听单元格编辑
        table.on('edit(vocabulary)', function (obj) {
            var value = obj.value //得到修改后的值
                , data = obj.data //得到所在行所有键值
                , field = obj.field;//得到字段
            $.ajax({
                url: '/Vocabulary/putVocabularyById',
                dataType: "text",
                type: 'post',
                data: 'vid=' + data.vid + '&' + field + '=' + value,
                success: function (data) {
                    var result = data;
                    alert(data);
                    if (data == 'error') {
                        layer.msg("修改失败");//失败的表情
                        return;
                    } else if (data == 'success') {
                        layer.msg('[ID: ' + data.vid + '] ' + field + ' 字段更改为：' + value + '修改成功');
                    }
                },
                complete: function () {
                    layer.close(this.layerIndex);
                },
            });
        });
    });
</script>
</body>
</html>