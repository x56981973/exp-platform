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
            <li><a href="#">学生列表</a></li>
        </ul>

        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header">
                    <h2><i class="halflings-icon user"></i><span class="break"></span>学生名单</h2>
                </div>
                <div class="box-content">
                    <table class="table table-bordered table-striped table-condensed">
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
                                    <span class="label label-important">未提交</span>
                                </td>
                                <td class="center">${s.s_score}</td>
                                <td class="center">
                                    <a class="btn btn-success" href="#">
                                        <i class="halflings-icon white zoom-in"></i>
                                    </a>
                                    <a class="btn btn-info" href="#">
                                        <i class="halflings-icon white edit"></i>
                                    </a>
                                    <a class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" data-name="${s.s_name}" data-deleteurl="/student/delete">
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
    </div>

    </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">取消</a>
        <a href="#" class="btn btn-primary">确认</a>
    </div>
</div>

<#include "include/footer.ftl">

<script type="text/javascript">
    $("#deleteModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        var name = button.data("name");
        var deleteUrl = button.data("deleteurl");
        var modal = $(this);
        modal.find('.modal-body').text('确认删除 '+name+' 吗?');
        modal.find('.modal-footer a').attr('href',deleteUrl);
    });
</script>

</body>
</html>