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
                <li><a href="#">学生列表</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon user"></i><span class="break"></span>学生名单</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#addModal"><i class="halflings-icon plus"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <table class="table table-striped table-bordered bootstrap-datatable datatable">
                        <#--<table class="table table-bordered table-striped table-condensed">-->
                            <thead>
                            <tr>
                                <th>学号</th>
                                <th>姓名</th>
                                <th>密码</th>
                                <th>年级</th>
                                <th>教师</th>
                                <th>成绩</th>
                                <th>报告状态</th>
                                <th>报告路径</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (student?size > 0)>
                                <#list student as s>
                                <tr>
                                    <td class="center">${s.s_login_name}</td>
                                    <td class="center">${s.s_name}</td>
                                    <td class="center">${s.s_password}</td>
                                    <td class="center">${s.s_grade}</td>
                                    <td class="center">${s.teacher}</td>
                                    <td class="center">${s.s_score}</td>
                                    <td class="center">${s.report_status}</td>
                                    <td class="center">${s.report_path}</td>
                                    <td class="center">
                                        <a class="btn btn-info" data-toggle="modal" data-target="#editModal"
                                           data-id="${s.s_login_name}" data-name="${s.s_name}" data-password="${s.s_password}"
                                           data-grade="${s.s_grade}" data-teacher="${s.teacher}" data-score="${s.s_score}"
                                           data-status="${s.report_status}" data-path="${s.report_path}">
                                            编辑
                                        </a>
                                        <a class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-name="${s.s_name}" data-id="${s.s_login_name}">
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

<div class="modal hide fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>学生编辑</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <form id="editForm">
                <label class="control-label">学号</label>
                <input class="input-xlarge" id="new_id" type="text" name="s_login_name" readOnly="true">
                <label class="control-label">姓名</label>
                <input class="input-xlarge focused" id="new_name" type="text" name="s_name">
                <label class="control-label">密码</label>
                <input class="input-xlarge focused" id="new_password" type="text" name="s_password">
                <label class="control-label">年级</label>
                <input class="input-xlarge focused" id="new_grade" type="text" name="s_grade">
                <label class="control-label">教师</label>
                <input class="input-xlarge focused" id="new_teacher" type="text" name="teacher">
                <label class="control-label">成绩</label>
                <input class="input-xlarge focused" id="new_score" type="text" name="s_score">
                <label class="control-label">报告状态</label>
                <input class="input-xlarge focused" id="new_status" type="text" name="report_status">
                <label class="control-label">报告路径</label>
                <input class="input-xlarge focused" id="new_path" type="text" name="report_path">
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <a class="btn btn-primary" id="postEdit">确认</a>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    </div>
</div>

<div class="modal hide fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加学生</h3>
    </div>
    <div class="modal-body">
        <form>
            <label class="control-label">学号</label>
            <input class="input-xlarge focused" id="new_id1" type="text">
            <label class="control-label">姓名</label>
            <input class="input-xlarge focused" id="new_name1" type="text">
            <label class="control-label">密码</label>
            <input class="input-xlarge focused" id="new_password1" type="text">
            <label class="control-label">年级</label>
            <input class="input-xlarge focused" id="new_grade1" type="text">
            <label class="control-label">教师</label>
            <input class="input-xlarge focused" id="new_teacher1" type="text">
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postAdd">确认</a>
    </div>
</div>

<#include "include/footer.ftl">

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
            url: '${base}/admin/student/delete',
            type: 'POST',
            data: $.param({'s_login_name':id}),
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

<script type="text/javascript">

    $("#editModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var id = button.data("id");
        var name = button.data("name");
        var password = button.data("password");
        var grade = button.data("grade");
        var teacher = button.data("teacher");
        var score = button.data("score");
        var status = button.data("status");
        var path = button.data("path");

        var modal = $(this);
        modal.find('#new_id').val(id);
        modal.find('#new_name').val(name);
        modal.find('#new_password').val(password);
        modal.find('#new_grade').val(grade);
        modal.find('#new_teacher').val(teacher);
        modal.find('#new_score').val(score);
        modal.find('#new_status').val(status);
        modal.find('#new_path').val(path);
    });

    $("#postEdit").click(function(){
        $.ajax({
            url: '${base}/admin/student/update',
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
                            function(){
                                location.reload();
                            });
                } else {
                    swal(data.msg,"","error");
                }
            }
        });
        $('#editModal').modal('hide');
    })
</script>

<script type="text/javascript">
    $("#postAdd").click(function(){
        var new_id = $('#new_id1').val();
        var new_name = $('#new_name1').val();
        var new_password = $('#new_password1').val();
        var new_grade = $('#new_grade1').val();
        var new_teacher = $('#new_teacher1').val();
        $.ajax({
            url: '${base}/admin/student/insert',
            type: 'POST',
            data: $.param({'s_login_name':new_id,'s_name':new_name,'s_password':new_password,
                            's_grade':new_grade,'t_login_name':new_teacher}),
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
        $('#addModal').modal('hide');
    })
</script>


</body>
</html>