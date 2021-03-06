<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

        <!-- start: Content -->
        <div id="content" class="span10">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="${base}/admin/home">首页</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>
                    <a href="${base}/admin/exp">实验列表</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="#">新增实验</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header" data-original-title>
                        <h2><i class="halflings-icon edit"></i><span class="break"></span>新增实验</h2>
                    </div>
                    <div class="box-content">
                        <form id="addExpForm" enctype="multipart/form-data" method="post">
                            <label class="control-label">编号</label>
                            <input class="input-xlarge" id="e_id" type="text" name="e_id">
                            <label class="control-label">实验名称</label>
                            <input class="input-xlarge" id="e_name" type="text" name="e_name">
                            <label class="control-label">实验简介</label>
                            <input class="input-xlarge" id="e_description" type="text" name="e_description">
                            <div class="control-group">
                                <label class="control-label">所属大类</label>
                                <div class="controls">
                                    <select id="class_info1" name="class_info" onchange=onSelected(this.options[this.options.selectedIndex].value)>
                                        <option value="-1">请选择</option>
                                    <#if (exp_class?size > 0)>
                                        <#list exp_class as e>
                                            <option value="${e.class_id}">${e.class_id} ${e.class_name}</option>
                                        </#list>
                                    </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">所属小类</label>
                                <div class="controls">
                                    <select id="type_info" name="type_info">
                                    </select>
                                </div>
                            </div>
                            <label class="control-label">文档上传</label>
                            <input class="input-file uniform_on" id="doc" type="file" name="doc">
                            <label class="control-label">参考代码上传</label>
                            <input class="input-file uniform_on" id="ref" type="file" name="ref">

                            <div class="control-group" style="margin-top: 20px; margin-left: 5px">
                                <button class="btn btn-primary" id="postAddExp">导入</button>
                                <button class="btn" id="reset">重置</button>
                            </div>
                        </form>
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

<#--add exp modal-->
<script type="text/javascript">
    $("#postAddExp").click(function(){
        if($('#e_id').val() == ""){
            swal("编号不能为空","","error");
        } else if($('#e_name').val() == ""){
            swal("实验名称不能为空","","error");
        } else if($('#e_description').val() == ""){
            swal("实验简介不能为空","","error");
        } else if($('#class_info1').val() == "-1"){
            swal("实验分类不能为空","","error");
        } else {
            var options = {
                url: '${base}/admin/exp/insert',
                success: function (result) {
                    var data = eval("(" + result + ")");
                    if (data.error == 0) {

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
                    } else {
                        swal(data.msg,"","error");
                    }
                }
            };

            $('#addExpForm').ajaxForm(options);
        }
    });
</script>


</body>
</html>