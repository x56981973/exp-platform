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
                                        <a class="btn btn-xs btn-info" data-toggle="modal" data-target="#editModal"
                                           data-id="${e.e_id}" data-name="${e.e_name}" data-class="${e.class_name}" data-type="${e.type_name}">
                                            <i class="halflings-icon white edit"></i>
                                        </a>
                                        <a class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-name="${e.e_name}" data-id="${e.e_id}" data-flag="exp">
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
                                        <a class="btn btn-info" data-toggle="modal" data-target="#editModal"
                                           data-id="${e.type_id}" data-tname="${e.type_name}" data-cname="${e.class_name}">
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
                                        <a class="btn btn-info" data-toggle="modal" data-target="#editModal"
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

<#--<div class="modal hide fade" id="addExpModal" tabindex="-1" role="dialog" aria-labelledby="addExpModalLabel">-->
    <#--<div class="modal-header">-->
        <#--<button type="button" class="close" data-dismiss="modal">×</button>-->
        <#--<h3>添加实验</h3>-->
    <#--</div>-->
    <#--<div class="modal-body">-->
        <#--<form id="addExpForm">-->
            <#--<label class="control-label">编号</label>-->
            <#--<input class="input" id="e_id" type="text" name="e_id">-->
            <#--<label class="control-label">实验名称</label>-->
            <#--<input class="input-xlarge" id="e_name" type="text" name="e_name">-->
            <#--<label class="control-label">实验简介</label>-->
            <#--<input class="input-xlarge" id="e_description" type="text" name="e_description">-->
            <#--<div class="control-group">-->
                <#--<label class="control-label">所属大类</label>-->
                <#--<div class="controls">-->
                    <#--<select id="class_info1" name="class_info" onchange=onselected(this.options[this.options.selectedIndex].value)>-->
                        <#--<option value="-1">请选择</option>-->
                    <#--<#if (exp_class?size > 0)>-->
                        <#--<#list exp_class as e>-->
                            <#--<option value="${e.class_id}">${e.class_id} ${e.class_name}</option>-->
                        <#--</#list>-->
                    <#--</#if>-->
                    <#--</select>-->
                <#--</div>-->
            <#--</div>-->
            <#--<div class="control-group">-->
                <#--<label class="control-label">所属小类</label>-->
                <#--<div class="controls">-->
                    <#--<select id="type_info" name="type_info">-->
                    <#--</select>-->
                <#--</div>-->
            <#--</div>-->
        <#--</form>-->
    <#--</div>-->
    <#--<div class="modal-footer">-->
        <#--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>-->
        <#--<a class="btn btn-primary" id="postAddExp">确认</a>-->
    <#--</div>-->
<#--</div>-->

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

<#--&lt;#&ndash;combine class & type&ndash;&gt;-->
<#--<script type="text/javascript">-->

<#--function onselected(value){-->
    <#--$("#type_info").empty();-->
    <#--<#if (exp_type?size > 0)>-->
        <#--<#list exp_type as e>-->
            <#--if(${e.class_id}==value){-->
                <#--$("#type_info").append("<option>${e.type_id} ${e.type_name}</option>");-->
            <#--}-->
        <#--</#list>-->
    <#--</#if>-->
<#--}-->

<#--</script>-->

<#--&lt;#&ndash;add exp modal&ndash;&gt;-->
<#--<script type="text/javascript">-->
    <#--$("#postAddExp").click(function(){-->
        <#--if($('#e_id').val() == ""){-->
            <#--swal("编号不能为空","","error");-->
        <#--} else if($('#e_name').val() == ""){-->
            <#--swal("实验名称不能为空","","error");-->
        <#--} else if($('#e_description').val() == ""){-->
            <#--swal("实验简介不能为空","","error");-->
        <#--} else if($('#class_info1').val() == "请选择"){-->
            <#--swal("实验分类不能为空","","error");-->
        <#--} else {-->
            <#--$.ajax({-->
                <#--url: '${base}/admin/exp/add',-->
                <#--type: 'POST',-->
                <#--data: $('#addExpForm').serialize(),-->
                <#--success: function (result) {-->
                    <#--var data = eval("(" + result + ")");-->
                    <#--if (data.error == 0) {-->
                        <#--swal({-->
                                    <#--title: data.msg,-->
                                    <#--text: "",-->
                                    <#--type: "success",-->
                                    <#--confirmButtonText: "确认"-->
                                <#--},-->
                                <#--function () {-->
                                    <#--location.reload();-->
                                <#--});-->
                    <#--} else {-->
                        <#--swal(data.msg, "", "error");-->
                    <#--}-->
                <#--}-->
            <#--});-->
            <#--$('#addExpModal').modal('hide');-->
        <#--}-->
    <#--})-->
<#--</script>-->

</body>
</html>