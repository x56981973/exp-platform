<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

    <!-- start: Content -->
    <div id="content" class="span10">
        <ul class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a href="${base}/">主页</a>
                <i class="icon-angle-right"></i>
            </li>
            <li><a href="#">设置</a></li>
        </ul>

        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header" data-original-title>
                    <h2><i class="halflings-icon edit"></i><span class="break"></span>账户设置</h2>
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <form class="form-horizontal" id="updateUser" enctype="multipart/form-data" method="post" target="rfFrame">
                        <div class="control-group">
                            <label class="control-label">登录名</label>
                            <div class="controls">
                                <span class="input-xlarge uneditable-input">${teacher.t_login_name}</span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">教师姓名</label>
                            <div class="controls">
                                <span class="input-xlarge uneditable-input">${teacher.t_name}</span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="focusedInput">学校</label>
                            <div class="controls">
                                <input class="input-xlarge focused" name="school" id="focusedInput" type="text" value=${teacher.t_school}>
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
                    <h2><i class="halflings-icon edit"></i><span class="break"></span>新增学生</h2>
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <form class="form-horizontal" id="insertStudent" enctype="multipart/form-data" method="post" target="rfFrame">
                        <div class="control-group">
                            <label class="control-label" for="s_login_name">学生登录名</label>
                            <div class="controls">
                                <input type="text" name="s_login_name" id="s_login_name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="s_name">学生姓名</label>
                            <div class="controls">
                                <input type="text" name="s_name" id="s_name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="s_password">学生登录密码</label>
                            <div class="controls">
                                <input type="text" name="s_password" id="s_password">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="selectError">年级</label>
                            <div class="controls">
                                <select id="selectError" data-rel="chosen" name="s_grade">
                                    <option>2011</option>
                                    <option>2012</option>
                                    <option>2013</option>
                                    <option>2014</option>
                                    <option>2015</option>
                                    <option>2016</option>
                                    <option>2017</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary" onclick="return insert()">新增</button>
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
                    <h2><i class="halflings-icon edit"></i><span class="break"></span>导入学生列表</h2>
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <form class="form-horizontal" id="fileUpload" enctype="multipart/form-data" method="post" target="rframe">
                        <div class="control-group">
                            <label class="control-label" >学生名单上传</label>
                            <div class="controls">
                                <input class="input-file uniform_on" id="studentList" type="file" name="studentList">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">文件格式模板下载</label>
                            <div class="controls">
                                <a href="${base}/upload/exp_type.json">点击下载</a>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">导入</button>
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

<#--<script type="text/javascript">-->
    <#--$('#download').click(function(){-->
        <#--$.ajax({-->
            <#--url: '${base}/download',-->
            <#--type: 'POST',-->
            <#--data: {"fileName":"学生名单模版.xlsx"}-->
        <#--});-->
    <#--});-->
<#--</script>-->

<script type="text/javascript">
    function insert()
    {
        var form = document.getElementById("insertStudent");
        if(form.s_login_name.value==""){
            toastr.error("登录名不能为空!");
            return false;
        }
        if(form.s_name.value==""){
            toastr.error("姓名不能为空!");
            return false;
        }
        if(form.s_password.value==""){
            toastr.error("密码不能为空!");
            return false;
        }

        $.ajax({
            url: '${base}/student/insert',
            type: 'POST',
            data: $('#insertStudent').serialize(),
            success: function (result) {
                var data = eval("(" + result + ")");
                if (data.error == 0) {
                    swal("插入成功!",data.msg,"success");
                    $('#insertStudent')[0].reset();
                } else {
                    swal("插入失败!",data.msg,"error");
                    $('#insertStudent')[0].reset();
                }
            }
        });
    }
</script>

<script type="text/javascript">
    function update()
    {
        var form = document.getElementById("updateUser");

        //全部无修改
        if(form.s_password2.value == "" && form.s_password1.value == ""){
            if(form.school.value == ${teacher.t_school}) {
                return false;
            }
        }

        //两次密码不一致
        if(form.s_password2.value != form.s_password1.value){
            toastr.error("两次密码不一致!");
            return false;
        }

        $.ajax({
            url: '${base}/teacher/update',
            type: 'POST',
            data: $.param({'t_login_name':"${teacher.t_login_name}",'t_name':"${teacher.t_name}",
                'school':form.school.value,'password':form.s_password2.value}),
            success: function (result) {
                var data = eval("(" + result + ")");
                if (data.error == 0) {
                    swal("保存成功!","","success");
                    $('#insertStudent')[0].reset();
                } else {
                    swal("更新失败!","请稍后再试","error");
                    $('#insertStudent')[0].reset();
                }
            }
        });
    }
</script>

<script type="text/javascript">
    var options = {
        url: '${base}/student/insertList',
        success: function (result) {
            var data = eval("(" + result + ")");
            if (data.error == 0) {
                swal(data.msg,"提示:密码默认与登录名相同","success");
            } else {
                swal(data.msg,"","error");
            }
        }
    };

    $('#fileUpload').ajaxForm(options);
</script>

</body>
</html>