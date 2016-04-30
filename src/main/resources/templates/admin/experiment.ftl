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
                <li><a href="${base}/exp">实验列表</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验列表</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#expModal"><i class="halflings-icon plus"></i></a>
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
                                    <td class="center">
                                        <#--<div class="btn-group">-->
                                            <#--<button class="btn">操作</button>-->
                                            <#--<button class="btn dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>-->
                                            <#--<ul class="dropdown-menu pull-right" role="menu">-->
                                                <#--<li>-->
                                                    <#--<a data-toggle="modal" data-target="#editModal"-->
                                                       <#--data-id="${e.e_id}" data-name="${e.e_name}" data-class="${e.class_name}" data-type="${e.type_name}">-->
                                                        <#--编辑-->
                                                    <#--</a>-->
                                                <#--</li>-->
                                                <#--<li>-->
                                                    <#--<a data-toggle="modal" data-target="#deleteModal"-->
                                                       <#--data-name="${e.e_name}" data-id="${e.e_id}">-->
                                                        <#--删除-->
                                                    <#--</a>-->
                                                <#--</li>-->
                                            <#--</ul>-->
                                        <#--</div>-->
                                        <a class="btn btn-xs btn-info" data-toggle="modal" data-target="#editModal"
                                           data-id="${e.e_id}" data-name="${e.e_name}" data-class="${e.class_name}" data-type="${e.type_name}">
                                            <i class="halflings-icon white edit"></i>
                                        </a>
                                        <a class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal"
                                           data-name="${e.e_name}" data-id="${e.e_id}">
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

                <div class="box span6">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验大类</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#classModal"><i class="halflings-icon plus"></i></a>
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
                                           data-name="${e.class_name}" data-id="${e.class_id}">
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

                <div class="box span6">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验小类</h2>
                        <div class="box-icon">
                            <a data-toggle="modal" data-target="#typeModal"><i class="halflings-icon plus"></i></a>
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
                                           data-name="${e.type_name}" data-id="${e.type_id}">
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

<#include "include/footer.ftl">

</body>
</html>