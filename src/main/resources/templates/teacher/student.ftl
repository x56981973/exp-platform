<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

    <!-- start: Content -->
    <div id="content" class="span10">
        <ul class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a href="${base}/teacher/home">主页</a>
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
                            <th>年级</th>
                            <th>报告</th>
                            <th>成绩</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (student?size > 0)>
                            <#list student as s>
                            <tr>
                                <td class="center">${s.s_login_name}</td>
                                <td class="center">${s.s_name}</td>
                                <td class="center">${s.s_grade}</td>
                                <td class="center">
                                    <#if s.report_status == "未提交">
                                        <span class="label label-important">${s.report_status}</span>
                                    <#else>
                                        <a class="label label-success" href="${base}/upload/${s.teacher}/${s.s_login_name}/${s.report_path}">
                                            ${s.report_status}
                                        </a>
                                    </#if>
                                </td>
                                <td class="center">${s.s_score}</td>
                                <td class="center">
                                    <a class="btn btn-success" data-toggle="modal" data-target="#detailModal"
                                       data-id="${s.s_login_name}" data-name="${s.s_name}" data-password="${s.s_password}"
                                       data-grade="${s.s_grade}" data-report="${s.report_status}" data-score="${s.s_score}"
                                    >
                                        <#--<i class="halflings-icon white zoom-in"></i>-->
                                        查看
                                    </a>
                                    <a class="btn btn-info" data-toggle="modal" data-target="#editModal"
                                       data-id="${s.s_login_name}" data-name="${s.s_name}"
                                       data-grade="${s.s_grade}" data-score="${s.s_score}">
                                        <#--<i class="halflings-icon white edit"></i>-->
                                        编辑
                                    </a>
                                    <a class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"
                                       data-name="${s.s_name}" data-id="${s.s_login_name}">
                                        <#--<i class="halflings-icon white trash"></i>-->
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

<div class="modal hide fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加学生</h3>
    </div>
    <div class="modal-body">
        <form id="insertStudent">
            <label class="control-label">学号</label>
            <input type="text" name="s_login_name" id="s_login_name">
            <label class="control-label">姓名</label>
            <input type="text" name="s_name" id="s_name">
            <label class="control-label">密码</label>
            <input type="text" name="s_password" id="s_password">
            <label class="control-label">年级</label>
            <select id="selectError" data-rel="chosen" name="s_grade">
                <option>2011</option>
                <option>2012</option>
                <option>2013</option>
                <option>2014</option>
                <option>2015</option>
                <option selected>2016</option>
                <option>2017</option>
            </select>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postAdd">确认</a>
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

<div class="modal hide fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>学生详情</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label class="control-label">学号</label>
            <span class="input-xlarge uneditable-input" id="id"></span>
            <label class="control-label">姓名</label>
            <span class="input-xlarge uneditable-input" id="name"></span>
            <label class="control-label">密码</label>
            <span class="input-xlarge uneditable-input" id="password"></span>
            <label class="control-label">年级</label>
            <span class="input-xlarge uneditable-input" id="grade"></span>
            <label class="control-label">成绩</label>
            <span class="input-xlarge uneditable-input" id="score"></span>
            <label class="control-label">报告提交情况</label>
            <span class="input-xlarge uneditable-input" id="report"></span>
            <label class="control-label"></label>
            <span id="progress"></span>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
    </div>
</div>

<div class="modal hide fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>学生编辑</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <form>
                <label class="control-label">学号</label>
                <span class="input-xlarge uneditable-input" id="id"></span>
                <label class="control-label">姓名</label>
                <input class="input-xlarge focused" id="edit_name" type="text">
                <label class="control-label">年级</label>
                <input class="input-xlarge focused" id="edit_grade" type="text">
                <label class="control-label">成绩</label>
                <input class="input-xlarge focused" id="edit_score" type="text">
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <a class="btn btn-primary" id="postEdit">确认</a>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
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
            url: '${base}/teacher/student/delete',
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
    $("#detailModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var id = button.data("id");
        var name = button.data("name");
        var password = button.data("password");
        var grade = button.data("grade");
        var score = button.data("score");
        var report = button.data("report");

        var modal = $(this);
        modal.find('#id').text(id);
        modal.find('#name').text(name);
        modal.find('#password').text(password);
        modal.find('#grade').text(grade);
        modal.find('#score').text(score);
        modal.find('#report').text(report);
    });
</script>

<script type="text/javascript">
    var id = "";
    var name = "";
    var grade = "";
    var score = "";

    $("#editModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        id = button.data("id");
        name = button.data("name");
        grade = button.data("grade");
        score = button.data("score");

        var modal = $(this);
        modal.find('#id').text(id);
        modal.find('#edit_name').val(name);
        modal.find('#edit_grade').val(grade);
        modal.find('#edit_score').val(score);
    });

    $("#postEdit").click(function(){
        var new_name = $('#edit_name').val();
        var new_grade = $('#edit_grade').val();
        var new_score = $('#edit_score').val();
        console.log(new_name);
        if(new_name == ''){
            swal("姓名不能为空","","error");
        } else if(new_grade == ''){
            swal("年级不能为空","","error");
        } else if(new_score == ''){
            swal("分数不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/teacher/student/update/info',
                type: 'POST',
                data: $.param({'s_login_name': id, 's_name': new_name, 's_grade': new_grade, 's_score': new_score}),
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
        }
    })
</script>

<script type="text/javascript">
    $("#postAdd").click(function(){
        var new_id = $('#s_login_name').val();
        var new_name = $('#s_name').val();
        var new_password = $('#s_password').val();
        if(new_id == ''){
            swal("学号不能为空","","error");
        } else if(new_name == ''){
            swal("姓名不能为空","","error");
        } else if(new_password == ''){
            swal("密码不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/teacher/student/insert',
                type: 'POST',
                data: $('#insertStudent').serialize(),
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
        }
    })
</script>

</body>
</html>