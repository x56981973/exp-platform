<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

        <!-- start: Content -->
        <div id="content" class="span10">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/admin/home">主页</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="/teacher">教师</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验列表</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#addModal"><i class="halflings-icon plus"></i></a>
                            <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <table class="table table-striped table-bordered bootstrap-datatable datatable">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>登录名</th>
                                <th>密码</th>
                                <th>学校</th>
                                <th>角色</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (teacher?size > 0)>
                                <#list teacher as t>
                                <tr>
                                    <td>${t.t_login_name}</td>
                                    <td class="center">${t.t_name}</td>
                                    <td class="center">${t.t_password}</td>
                                    <td class="center">${t.t_school}</td>
                                    <td class="center">${t.role}</td>
                                    <td class="center">
                                        <a class="btn btn-info" data-toggle="modal" data-target="#editModal"
                                           data-id="${t.t_login_name}" data-name="${t.t_name}" data-password="${t.t_password}"
                                           data-grade="${t.t_school}" data-role="${t.role}" >
                                            编辑
                                        </a>
                                        <a class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-id="${t.t_login_name}" data-name="${t.t_name}">
                                            删除
                                        </a>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="modal hide fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="checkDelete">确认</a>
    </div>
</div>

<div class="modal hide fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加教师</h3>
    </div>
    <div class="modal-body">
        <form id="addForm">
            <label class="control-label">登录名</label>
            <input class="input-xlarge focused" id="t_login_name" type="text" name="t_login_name">
            <label class="control-label">姓名</label>
            <input class="input-xlarge focused" id="t_name" type="text" name="t_name">
            <label class="control-label">密码</label>
            <input class="input-xlarge focused" id="t_password" type="text" name="t_password">
            <label class="control-label">学校</label>
            <input class="input-xlarge focused" id="t_school" type="text" name="t_school">
            <div class="control-group">
                <label class="control-label">角色</label>
                <div class="controls">
                    <select id="role" name="role">
                        <option>user</option>
                        <option>admin</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postAdd">确认</a>
    </div>
</div>

<div class="modal hide fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加教师</h3>
    </div>
    <div class="modal-body">
        <form id="editForm">
            <label class="control-label">登录名</label>
            <input class="input-xlarge" id="new_login_name" type="text" name="t_login_name" readOnly="true">
            <label class="control-label">姓名</label>
            <input class="input-xlarge focused" id="new_name" type="text" name="t_name">
            <label class="control-label">密码</label>
            <input class="input-xlarge focused" id="new_password" type="text" name="t_password">
            <label class="control-label">学校</label>
            <input class="input-xlarge focused" id="new_school" type="text" name="t_school">
            <div class="control-group">
                <label class="control-label">角色</label>
                <div class="controls">
                    <select id="new_role" name="role">
                        <option value="user">user</option>
                        <option value="admin">admin</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postEdit">确认</a>
    </div>
</div>

<#include "include/footer.ftl">

<#--delete modal-->
<script type="text/javascript">
    var id = "";

    $("#deleteModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var name = button.data("name");
        id = button.data("id");
        var modal = $(this);
        modal.find('.modal-body').text('确认删除 '+name+' 吗?');
    });

    $("#checkDelete").click(function(){
        $.ajax({
            url: '${base}/admin/teacher/delete',
            type: 'POST',
            data: $.param({'t_login_name':id}),
            success: function (result) {
                var data = eval("(" + result + ")");
                if (data.error == 0) {
                    swal({
                                title: data.msg,
                                text: "",
                                type: "success",
                                confirmButtonText: "确认"
                            },
                            function(){
                                location.reload();
                            });
                } else {
                    swal(data.msg,"","error");
                }
            }
        });
        $('#deleteModal').modal('hide');
    })
</script>

<#--add modal-->
<script type="text/javascript">
    $("#postAdd").click(function(){
        if($('#t_login_name').val() == ""){
            swal("登陆名不能为空","","error");
        } else if($('#t_name').val() == ""){
            swal("姓名不能为空","","error");
        } else if($('#t_password').val() == ""){
            swal("密码不能为空","","error");
        } else if($('#t_school').val() == ""){
            swal("学校不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/teacher/insert',
                type: 'POST',
                data: $('#addForm').serialize(),
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
                                    location.reload();
                                });
                    } else {
                        swal(data.msg, "", "error");
                    }
                }
            });
            $('#editModal').modal('hide');
        }
    })
</script>

<#--edit modal-->
<script type="text/javascript">

    $("#editModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var id = button.data("id");
        var name = button.data("name");
        var password = button.data("password");
        var grade = button.data("grade");
        var role = button.data("role");

        var modal = $(this);
        modal.find('#new_login_name').val(id);
        modal.find('#new_name').val(name);
        modal.find('#new_password').val(password);
        modal.find('#new_school').val(grade);
        if(role == "admin"){
            modal.find("#new_role option[value='admin']").attr("selected",true);
        } else{
            modal.find("#new_role option[value='user']").attr("selected",true);
        }
    });

    $("#postEdit").click(function(){
        if($('#new_login_name').val() == ""){
            swal("登陆名不能为空","","error");
        } else if($('#new_name').val() == ""){
            swal("姓名不能为空","","error");
        } else if($('#new_password').val() == ""){
            swal("密码不能为空","","error");
        } else if($('#new_school').val() == ""){
            swal("学校不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/teacher/update',
                type: 'POST',
                data: $('#editForm').serialize(),
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
                                    location.reload();
                                });
                    } else {
                        swal(data.msg, "", "error");
                    }
                }
            });
            $('#editModal').modal('hide');
        }
    })
</script>

</body>
</html>