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
</head>
<body>

<table class="layui-hide" id="demo" lay-filter="test"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<div class="layui-tab layui-tab-brief" lay-filter="demo">
    <ul class="layui-tab-title">
        <li class="layui-this">演示说明</li>
        <li>上传</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item">
            <div class="layui-upload-drag" id="uploadDemo">
                <i class="layui-icon"></i>
                <p>点击上传，或将文件拖拽到此处</p>
                <div class="layui-hide" id="uploadDemoView">
                    <hr>
                    <img src="" alt="上传成功后渲染" style="max-width: 100%">
                </div>
            </div>
        </div>
    </div>
</div>

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
            ,height: 420
            ,url: '/Vocabulary/getVocabulary' //数据接口
            ,title: '词汇表'
            ,page: true //开启分页
            ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            ,totalRow: true //开启合计行
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'vid', title: '单词编号', width:80, sort: true, fixed: 'left'}
                ,{field: 'abbreviation', title: '缩写',}
                ,{field: 'word', title: '单词', sort: true}
                ,{field: 'translation', title: '翻译'}
                ,{field: 'translator', title: '翻译人'}
                ,{field: 'time', title: '翻译时间', width:150}
                ,{field: 'group', title: '所属翻译小组', width: 200}
                ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
            ]]
        });

        //监听头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    layer.msg('添加');
                    break;
                case 'update':
                    if(data.length === 0){
                        layer.msg('请选择一行');
                    } else if(data.length > 1){
                        layer.msg('只能同时编辑一个');
                    } else {
                        layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                    }
                    break;
                case 'delete':
                    if(data.length === 0){
                        layer.msg('请选择一行');
                    } else {
                        layer.msg('删除');
                    }
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'detail'){
                layer.msg('查看操作');
            } else if(layEvent === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if(layEvent === 'edit'){
                layer.msg('编辑操作');
            }
        });

        //将日期直接嵌套在指定容器中
        var dateIns = laydate.render({
            elem: '#laydateDemo'
            ,position: 'static'
            ,calendar: true //是否开启公历重要节日
            ,done: function(value, date, endDate){
                if(date.year == 2017 && date.month == 11 && date.date == 30){
                    dateIns.hint('一不小心就月底了呢');
                }
            }
            ,change: function(value, date, endDate){
                layer.msg(value)
            }
        });

        //上传
        upload.render({
            elem: '#uploadDemo'
            ,url: 'https://httpbin.org/post' //改成您自己的上传接口
            ,done: function(res){
                layer.msg('上传成功');
                layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.files.file);
                console.log(res)
            }
        });

        //底部信息
        var footerTpl = lay('#footer')[0].innerHTML;
        lay('#footer').html(layui.laytpl(footerTpl).render({}))
            .removeClass('layui-hide');
    });
</script>
</body>
</html>