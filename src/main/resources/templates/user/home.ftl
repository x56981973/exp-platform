<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

    <!-- start: Content -->
    <div id="content" class="span10">
        <ul class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a href="${base}/user/home">首页</a>
                <i class="icon-angle-right"></i>
            </li>
            <li><a href="#">概览</a></li>
        </ul>

        <div class="row-fluid">

            <div class="span3 statbox purple">
                <div class="boxchart">5,6,7,2,0,4,2,4,8,2,3,3,2</div>
                <div class="number">${s_num}<i class="icon-arrow-up"></i></div>
                <div class="title">学生总数</div>
                <div class="footer">
                    <a href="${base}/user/student"> 查看学生</a>
                </div>
            </div>
            <div class="span3 statbox green">
                <div class="boxchart">5,6,7,2,0,-4,-2,4,8,2,3,3,2</div>
                <div class="number">${e_num}<i class="icon-arrow-up"></i></div>
                <div class="title">实验总数</div>
                <div class="footer">
                    <a href="${base}/user/exp"> 查看实验</a>
                </div>
            </div>

            <div class="span3 statbox blue noMargin">
                <div class="boxchart">5,6,7,2,0,-4,-2,4,8,2,3,3,2</div>
                <div class="number">8<i class="icon-arrow-up"></i></div>
                <div class="title">消息</div>
                <div class="footer">
                    <a href="#"> 查看消息</a>
                </div>
            </div>

            <div class="span3 statbox orangeDark">
                <div class="boxchart">7,2,2,2,1,-4,-2,4,8,-2,0,3,3,5</div>
                <div class="number"><i class="icon-cogs"></i></div>
                <div class="title">设置</div>
                <div class="footer">
                    <a href="${base}/user/settings"> 进入设置</a>
                </div>
            </div>
        </div>

        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header">
                    <h2><i class="halflings-icon align-justify"></i><span class="break"></span>正在进行中的实验</h2>
                    <div class="box-icon">
                        <a data-toggle="modal" data-target="#editModal"><i class="halflings-icon wrench"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <table class="table table-bordered table-striped table-condensed">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>实验名称</th>
                            <th>实验介绍</th>
                            <th>开放参考</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (active_exp?size > 0)>
                            <#list active_exp as e>
                            <tr>
                                <td>${e.e_id}</td>
                                <td class="center">${e.e_name}</td>
                                <td class="center">${e.e_description}</td>
                                <#if e.status == "+">
                                    <td class="center">
                                        <a class="btn btn-small btn-success" id="${e.e_id}" onclick="flagSwitch('${e.e_id}','${e.status}')">开放</a>
                                    </td>
                                <#else>
                                    <td class="center">
                                        <a class="btn btn-small btn-danger" id="${e.e_id}" onclick="flagSwitch('${e.e_id}','${e.status}')">关闭</a>
                                    </td>
                                </#if>
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

<div class="modal hide fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑</h3>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <#if (exp?size > 0)>
                <#list exp as e>
                <label class="checkbox">
                    <input type="checkbox" class="${e.e_id}"> ${e.e_id} ${e.e_name}
                </label>
                </#list>
            </#if>
        </div>
    </div>
    <div class="modal-footer">
        <a class="btn btn-primary" id="postEdit">保存</a>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    </div>
</div>

<#include "include/footer.ftl">

<script type="text/javascript">
    <#if (active_exp?size > 0)>
        <#list active_exp as e>
        $('.'+'${e.e_id}').attr("checked",true);
        </#list>
    </#if>

    $("#postEdit").click(function(){
        var list = '';
        $('input:checkbox').each(function() {
            if ($(this).attr('checked') == "checked") {
                list += $(this).attr("class") + ",";
            }
        });

        list = list.substring(0,list.length-1);

        $.ajax({
            url: '${base}/user/teacher/changeActiveExp',
            type: 'POST',
            data: $.param({'list':list}),
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
   function flagSwitch(id,status){
       if(status == '+') {
           status = '-';
       }else{
           status = '+';
       }

       var current = $('#'+id).text();
       if(current == "开放"){
           $('#'+id).removeClass("btn-success").addClass("btn-danger").text("关闭");
       } else {
           $('#'+id).removeClass("btn-danger").addClass("btn-success").text("开放");
       }

       $.ajax({
           url: '${base}/user/teacher/changeExpStatus',
           type: 'POST',
           data: $.param({'e_id':id,'status':status}),
           success: function (result) {
               var data = eval("(" + result + ")");
               if (data.error != 0) {
                   swal(data.msg,"","error");
               }
           }
       });
   }

</script>

</body>
</html>