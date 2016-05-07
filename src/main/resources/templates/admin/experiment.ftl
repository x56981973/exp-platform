<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

        <!-- start: Content -->
        <div id="content" class="span10">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="${base}/admin/">主页</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="${base}/admin/exp">实验列表</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验列表</h2>
                        <div class="box-icon">
                            <a href="${base}/admin/exp/add"><i class="halflings-icon plus"></i></a>
                            <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <table class="table table-striped table-bordered bootstrap-datatable datatable">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>实验名称</th>
                                <th>实验简介</th>
                                <th>所属大类</th>
                                <th>所属小类</th>
                                <#--<th>文档路径</th>-->
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (exp?size > 0)>
                                <#list exp as e>
                                <tr>
                                    <td>${e.e_id}</td>
                                    <td class="center">${e.e_name}</td>
                                    <td class="center">${e.e_description}</td>
                                    <td class="center">${e.class_name}</td>
                                    <td class="center">${e.type_name}</td>
                                    <#--<td class="center">${e.e_srcPath}</td>-->
                                    <td class="center">
                                        <a class="btn btn-xs btn-info" href="${base}/admin/exp/edit/${e.e_id}">
                                            <i class="halflings-icon white edit"></i>
                                        </a>
                                        <a class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-name="${e.e_name}" data-id="${e.e_id}" data-flag="exp">
                                            <i class="halflings-icon white trash"></i>
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

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验小类</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#addTypeModal"><i class="halflings-icon plus"></i></a>
                            <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <table class="table table-bordered table-striped table-condensed">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>名称</th>
                                <th>所属大类</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (exp_type?size > 0)>
                                <#list exp_type as e>
                                <tr>
                                    <td>${e.type_id}</td>
                                    <td class="center">${e.type_name}</td>
                                    <td class="center">${e.class_name}</td>
                                    <td class="center">
                                        <a class="btn btn-info" data-toggle="modal" data-target="#editTypeModal"
                                           data-tid="${e.type_id}" data-tname="${e.type_name}" data-cname="${e.class_name}" data-cid="${e.class_id}">
                                            <i class="halflings-icon white edit"></i>
                                        </a>
                                        <a class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-name="${e.type_name}" data-id="${e.type_id}" data-flag="expType">
                                            <i class="halflings-icon white trash">
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

            <div class="row-fluid">

                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验大类</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#addClassModal"><i class="halflings-icon plus"></i></a>
                            <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>名称</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (exp_class?size > 0)>
                                <#list exp_class as e>
                                <tr>
                                    <td>${e.class_id}</td>
                                    <td class="center">${e.class_name}</td>
                                    <td class="center">
                                        <a class="btn btn-info" data-toggle="modal" data-target="#editClassModal"
                                           data-id="${e.class_id}" data-name="${e.class_name}">
                                            <i class="halflings-icon white edit"></i>
                                        </a>
                                        <a class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-name="${e.class_name}" data-id="${e.class_id}" data-flag="expClass">
                                            <i class="halflings-icon white trash">
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

<div class="modal hide fade" id="addClassModal" tabindex="-1" role="dialog" aria-labelledby="addClassModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加大类</h3>
    </div>
    <div class="modal-body">
        <form id="addClassForm">
            <label class="control-label">编号</label>
            <input class="input" id="class_id" type="text" name="class_id">
            <label class="control-label">大类名称</label>
            <input class="input" id="class_name" type="text" name="class_name">
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postAddClass">确认</a>
    </div>
</div>

<div class="modal hide fade" id="editClassModal" tabindex="-1" role="dialog" aria-labelledby="editClassModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑大类</h3>
    </div>
    <div class="modal-body">
        <form id="editClassForm">
            <label class="control-label">编号</label>
            <input class="input" id="class_id1" type="text" name="class_id">
            <label class="control-label">大类名称</label>
            <input class="input" id="class_name1" type="text" name="class_name">
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postEditClass">确认</a>
    </div>
</div>

<div class="modal hide fade" id="addTypeModal" tabindex="-1" role="dialog" aria-labelledby="addTypeModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>添加小类</h3>
    </div>
    <div class="modal-body">
        <form id="addTypeForm">
            <label class="control-label">编号</label>
            <input class="input" id="type_id" type="text" name="type_id">
            <label class="control-label">小类名称</label>
            <input class="input" id="type_name" type="text" name="type_name">
            <div class="control-group">
                <label class="control-label">所属大类</label>
                <div class="controls">
                    <select id="class_info" name="class_info">
                        <#if (exp_class?size > 0)>
                            <#list exp_class as e>
                                <option>${e.class_id} ${e.class_name}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postAddType">确认</a>
    </div>
</div>

<div class="modal hide fade" id="editTypeModal" tabindex="-1" role="dialog" aria-labelledby="editTypeModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑小类</h3>
    </div>
    <div class="modal-body">
        <form id="editTypeForm">
            <label class="control-label">编号</label>
            <input class="input" id="type_id1" type="text" name="type_id" readonly="true">
            <label class="control-label">小类名称</label>
            <input class="input" id="type_name1" type="text" name="type_name">
            <div class="control-group">
                <label class="control-label">所属大类</label>
                <div class="controls">
                    <select id="class_info1" name="class_info">
                    <#if (exp_class?size > 0)>
                        <#list exp_class as e>
                            <option value="${e.class_id} ${e.class_name}">${e.class_id} ${e.class_name}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a class="btn btn-primary" id="postEditType">确认</a>
    </div>
</div>

<#include "include/footer.ftl">

<#--delete modal-->
<script type="text/javascript">
    var id = "";
    var flag = "";

    $("#deleteModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var name = button.data("name");
        id = button.data("id");
        flag = button.data("flag");
        var modal = $(this);
        modal.find('.modal-body').text('确认删除 '+name+' 吗?');
    });

    $("#checkDelete").click(function(){
        $.ajax({
            url: '${base}/admin/'+flag + '/delete',
            type: 'POST',
            data: $.param({'id':id}),
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

<#--add class modal-->
<script type="text/javascript">
    $("#postAddClass").click(function(){
        if($('#class_id').val() == ""){
            swal("编号不能为空","","error");
        } else if($('#class_name').val() == ""){
            swal("大类名不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/expClass/add',
                type: 'POST',
                data: $('#addClassForm').serialize(),
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
            $('#addClassModal').modal('hide');
        }
    })
</script>

<#--edit class modal-->
<script type="text/javascript">
    $("#editClassModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var name = button.data("name");
        var id = button.data("id");

        var modal = $(this);
        modal.find('#class_id1').val(id);
        modal.find('#class_name1').val(name);
    });

    $("#postEditClass").click(function(){
        if($('#class_id1').val() == ""){
            swal("编号不能为空","","error");
        } else if($('#class_name1').val() == ""){
            swal("大类名不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/expClass/edit',
                type: 'POST',
                data: $('#editClassForm').serialize(),
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
            $('#editClassModal').modal('hide');
        }
    })
</script>

<#--add type modal-->
<script type="text/javascript">
    $("#postAddType").click(function(){
        if($('#type_id').val() == ""){
            swal("编号不能为空","","error");
        } else if($('#type_name').val() == ""){
            swal("小类名不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/expType/add',
                type: 'POST',
                data: $('#addTypeForm').serialize(),
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
            $('#addTypeModal').modal('hide');
        }
    })
</script>

<#--edit type modal-->
<script type="text/javascript">
    $("#editTypeModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var tname = button.data("tname");
        var cname = button.data("cname");
        var tid = button.data("tid");
        var cid = button.data("cid");

        var modal = $(this);
        modal.find('#type_id1').val(tid);
        modal.find('#type_name1').val(tname);
        modal.find('#class_info1').val(cid+" "+cname);
    });

    $("#postEditType").click(function(){
        if($('#type_id1').val() == ""){
            swal("编号不能为空","","error");
        } else if($('#type_name1').val() == ""){
            swal("小类名不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/expType/edit',
                type: 'POST',
                data: $('#editTypeForm').serialize(),
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
            $('#editTypeModal').modal('hide');
        }
    })
</script>

</body>
</html>