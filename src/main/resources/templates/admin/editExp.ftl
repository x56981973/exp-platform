<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

        <!-- start: Content -->
        <div id="content" class="span10">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="${base}/admin">首页</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>
                    <a href="${base}/admin/exp">实验列表</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="#">编辑实验</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header" data-original-title>
                        <h2><i class="halflings-icon edit"></i><span class="break"></span>编辑实验</h2>
                    </div>
                    <div class="box-content">
                        <form id="editExpForm">
                            <label class="control-label">编号</label>
                            <input class="input-xlarge" id="e_id" type="text" name="e_id" value="${experiment.e_id}" readOnly="true">
                            <label class="control-label">实验名称</label>
                            <input class="input-xlarge focused" id="e_name" type="text" name="e_name" value="${experiment.e_name}">
                            <label class="control-label">实验简介</label>
                            <input class="input-xlarge focused" id="e_description" type="text" name="e_description" value="${experiment.e_description}">
                            <label class="control-label">实验文件路径</label>
                            <input class="input-xlarge focused" id="e_srcPath" type="text" name="e_srcPath"
                                   <#if experiment.e_srcPath??>
                                   value="${experiment.e_srcPath}"
                                   </#if>
                                    >
                            <div class="control-group">
                                <label class="control-label">所属大类</label>
                                <div class="controls">
                                    <select id="class_info1" name="class_info" onchange=onSelected(this.options[this.options.selectedIndex].value)>
                                        <#if (exp_class?size > 0)>
                                            <#list exp_class as e>
                                                <#if ("${e.class_id}" == experiment.class_id)>
                                                    <option value="${e.class_id}" selected = "selected">${e.class_id} ${e.class_name}</option>
                                                <#else>
                                                    <option value="${e.class_id}">${e.class_id} ${e.class_name}</option>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">所属小类</label>
                                <div class="controls">
                                    <select id="type_info" name="type_info">
                                        <#if (exp_type?size > 0)>
                                            <#list exp_type as t>
                                                <#if (t.class_id == experiment.class_id)>
                                                    <#if "${t.type_id}" == experiment.type_id>
                                                        <option value="${t.type_id}" selected = "selected">${t.type_id} ${t.type_name}</option>
                                                    <#else>
                                                        <option value="${t.type_id}">${t.type_id} ${t.type_name}</option>
                                                    </#if>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </select>
                                </div>
                            </div>
                        </form>
                        <div>
                            <button class="btn btn-primary" id="postEditExp">修改</button>
                            <button class="btn" id="back">返回</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<#include "include/footer.ftl">

<#--combine class & type-->
<script type="text/javascript">

    function onSelected(value){
        $("#type_info").empty();
    <#if (exp_type?size > 0)>
        <#list exp_type as e>
            if(${e.class_id}==value){
            $("#type_info").append("<option>${e.type_id} ${e.type_name}</option>");
        }
        </#list>
    </#if>
    }

</script>

<#--back-->
<script type="text/javascript">
    $("#back").click(function(){
        window.location.href="${base}/admin/exp";
    });
</script>

<#--add exp modal-->
<script type="text/javascript">
    $("#postEditExp").click(function(){
        if($('#e_id').val() == ""){
            swal("编号不能为空","","error");
        } else if($('#e_name').val() == ""){
            swal("实验名称不能为空","","error");
        } else if($('#e_description').val() == ""){
            swal("实验简介不能为空","","error");
        } else {
            $.ajax({
                url: '${base}/admin/exp/edit',
                type: 'POST',
                data: $('#editExpForm').serialize(),
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
                                    window.location.href= "${base}"+"/admin/exp";
                                });
                    } else {
                        swal(data.msg, "", "error");
                    }
                }
            });
        }
    });
</script>

</body>
</html>