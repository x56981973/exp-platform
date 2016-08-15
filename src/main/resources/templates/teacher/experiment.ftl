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
                <li><a href="#">实验列表</a></li>
            </ul>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon align-justify"></i><span class="break"></span>实验列表</h2>
                        <div class="box-icon">
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
                                <#--<th>开放参考</th>-->
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
                                    <#--<td class="center">-->
                                        <#--<span class="label label-success">开放</span>-->
                                    <#--</td>-->
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
                            <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>编号</th>
                                <th>名称</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if (exp_class?size > 0)>
                                <#list exp_class as e>
                                <tr>
                                    <td>${e.class_id}</td>
                                    <td class="center">${e.class_name}</td>
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
                            </tr>
                            </thead>
                            <tbody>
                            <#if (exp_type?size > 0)>
                                <#list exp_type as e>
                                <tr>
                                    <td>${e.type_id}</td>
                                    <td class="center">${e.type_name}</td>
                                    <td class="center">${e.class_name}</td>
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
</div>

<#include "include/footer.ftl">

</body>
</html>