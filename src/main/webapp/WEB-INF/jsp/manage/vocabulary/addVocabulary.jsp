<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>添加词汇</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script type="application/javascript" src="/js/jquery.min.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加词汇</legend>
</fieldset>

<form class="layui-form" id="addV">
    <div class="layui-form-item">
        <label class="layui-form-label">缩写</label>
        <div class="layui-input-inline">
            <input type="text" name="abbreviation" autocomplete="off" placeholder="如果存在，请输入缩写" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">单词</label>
        <div class="layui-input-inline">
            <input type="text" name="word" autocomplete="off" placeholder="请输入单词" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">词性</label>
            <div class="layui-input-inline">
                <select name="cixing" lay-verify="required" lay-search="" id="cixing">
                    <option value="">直接选择或搜索选择</option>
                    <option value="n. ">n. 名词</option>
                    <option value="v. ">v. 动词 </option>
                    <option value="pron. ">pron. 代词</option>
                    <option value="adj. ">adj. 形容词 </option>
                    <option value="adv. ">adv. 副词</option>
                    <option value="num. ">num.数词</option>
                    <option value="art. ">art. 冠词</option>
                    <option value="prep. ">prep. 介词 </option>
                    <option value="conj. ">conj. 连词</option>
                    <option value="interj. ">interj. 感叹词</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">翻译</label>
        <div class="layui-input-inline">
            <input type="text" name="translation" autocomplete="off" placeholder="请输入" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
    <center>
        <button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件 (上传时间较长，请耐心等待)</button>
    </center>
    <center>
        <a href="/Vocabulary/downloadFile">下载指定上传文件</a>
    </center>

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
                    url: '/Vocabulary/postNewVocabulary',
                    dataType: "text",
                    type: 'post',
                    data: $("#addV").serialize(),
                    success: function (result) {
                        if (result == "success") {
                            layer.msg('已经成功添加', function(){
                                $("#addV").reset();
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

    layui.use('upload', function() {
        var $ = layui.jquery
            , upload = layui.upload;
        upload.render({ //允许上传的文件后缀
            elem: '#test3'
            , url: '/Vocabulary/uploadFile' //改成您自己的上传接口
            ,size: 10240
            // ,accept: 'file' //普通文件
            , exts: 'xls|xlsx' //只允许上传压缩文件
            , done: function (res) {
                layer.msg(res);
                layer.msg('上传成功');
                console.log(res)
            }
        });
    })
</script>

</body>
</html>
