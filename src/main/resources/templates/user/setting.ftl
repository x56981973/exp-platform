<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

    <!-- start: Content -->
    <div id="content" class="span10">
        <ul class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a href="/home">主页</a>
                <i class="icon-angle-right"></i>
            </li>
            <li><a href="#">设置</a></li>
        </ul>

        <#--<button class="btn btn-primary" id="test" onclick="test()">test</button>-->
        <#--<script>-->
            <#--function test(){-->
                <#--toastr.warning('只能选择一行进行编辑');-->
            <#--}-->
        <#--</script>-->

        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header" data-original-title>
                    <h2><i class="halflings-icon edit"></i><span class="break"></span>账户设置</h2>
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <form class="form-horizontal">
                        <fieldset>
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
                                    <input class="input-xlarge focused" id="focusedInput" type="text" value=${teacher.t_school}>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">新密码</label>
                                <div class="controls">
                                    <input type="text">
                                </div>
                            </div>
                            <div class="control-group error">
                                <label class="control-label" for="inputError">再次输入新密码</label>
                                <div class="controls">
                                    <input type="text" id="inputError">
                                </div>
                            </div>

                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary">保存设置</button>
                                <button class="btn">取消</button>
                            </div>
                        </fieldset>
                    </form>
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
                    <form class="form-horizontal">
                        <fieldset>
                            <div class="control-group">
                                <label class="control-label" for="fileInput">学生名单上传</label>
                                <div class="controls">
                                    <input class="input-file uniform_on" id="fileInput" type="file">
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">文件格式模板下载</label>
                                <div class="controls">
                                    <a href="#">导入文件格式下载</a>
                                </div>
                            </div>
                            <div class="form-actions">
                                <button type="submit" class="btn btn-primary">导入</button>
                                <button class="btn">取消</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>

    </div>
</div>

<div class="modal hide fade" id="myModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Settings</h3>
    </div>
    <div class="modal-body">
        <p>Here settings can be configured...</p>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">Close</a>
        <a href="#" class="btn btn-primary">Save changes</a>
    </div>
</div>

<#include "include/footer.ftl">

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
            url: '/student/insert',
            type: 'POST',
            data: $('#insertStudent').serialize(),
            success: function (result) {
                var data = eval("(" + result + ")");
                console.log(data);
                if (data.error == 0) {
                    swal("插入成功!","","success");
                    $('#insertStudent')[0].reset();
                } else {
                    swal("插入失败!","","error");
                    $('#insertStudent')[0].reset();
                }
            }
        });
    }
</script>

</body>
</html>