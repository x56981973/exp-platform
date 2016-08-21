<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

    <!-- start: Content -->
    <div id="content" class="span10">
        <ul class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a href="${base}/student/home">主页</a>
                <i class="icon-angle-right"></i>
            </li>
            <li><a href="#">设置</a></li>
        </ul>

        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header" data-original-title>
                    <h2><i class="halflings-icon edit"></i><span class="break"></span>账户设置</h2>
                </div>
                <div class="box-content">
                    <form class="form-horizontal" id="updateUser" enctype="multipart/form-data" method="post" target="rfFrame">
                        <div class="control-group">
                            <label class="control-label">登录名</label>
                            <div class="controls">
                                <span class="input-xlarge uneditable-input">${student.s_login_name}</span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">姓名</label>
                            <div class="controls">
                                <span class="input-xlarge uneditable-input">${student.s_name}</span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">新密码</label>
                            <div class="controls">
                                <input type="text" name="s_password1" id="s_password1">
                            </div>
                        </div>
                        <div class="control-group error">
                            <label class="control-label" for="inputError">再次输入新密码</label>
                            <div class="controls">
                                <input type="text" name="s_password2" id="s_password2">
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" onclick="return update()">保存设置</button>
                            <button type="reset" class="btn">取消</button>
                        </div>
                    </form>
                    <iframe style="display: none" id="rfFrame" name="rfFrame" src="about:blank"></iframe>
                </div>
            </div>
        </div>

        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header" data-original-title>
                    <h2><i class="halflings-icon edit"></i><span class="break"></span>实验报告上传</h2>
                </div>
                <div class="box-content">
                    <form class="form-horizontal" id="fileUpload" enctype="multipart/form-data" method="post" target="rframe">
                        <div class="control-group">
                            <label class="control-label">文件格式模板下载</label>
                            <div class="controls">
                                <a href="${base}/file/实验报告模板.pdf">点击下载</a>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" >实验报告终稿上传</label>
                            <div class="controls">
                                <input class="input-file uniform_on" id="report" type="file" name="report">
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">上传</button>
                            <button type="reset" id="reset" class="btn">取消</button>
                        </div>
                    </form>
                    <iframe style="display: none" id="rfFrame" name="rfFrame" src="about:blank"></iframe>
                </div>
            </div>
        </div>
    </div>

    </div>
</div>

<#include "include/footer.ftl">

<script type="text/javascript">
    function update()
    {
        var form = document.getElementById("updateUser");

        //全部无修改
        if(form.s_password2.value == "" && form.s_password1.value == ""){
            return false;
        }

        //两次密码不一致
        if(form.s_password2.value != form.s_password1.value){
            toastr.error("两次密码不一致!");
            return false;
        }

        $.ajax({
            url: '${base}/student/update',
            type: 'POST',
            data: $.param({'s_login_name':"${student.s_login_name}",'password':form.s_password2.value}),
            success: function (result) {
                var data = eval("(" + result + ")");
                if (data.error == 0) {
                    swal({
                            title: data.msg,
                            text: "",
                            type: "success",
                            confirmButtonText: "确认"
                        },
                        function () {
                            window.location.href= "${base}"+"/login";
                        });
                } else {
                    swal("更新失败!","请稍后再试","error");
                }
            }
        });
    }
</script>

<script type="text/javascript">
    var options = {
        url: '${base}/student/upload/report',
        success: function (result) {
            var data = eval("(" + result + ")");
            if (data.error == 0) {
                swal(data.msg,"","success");
            } else {
                swal(data.msg,"","error");
            }
        }
    };

    $('#fileUpload').ajaxForm(options);
</script>

</body>
</html>