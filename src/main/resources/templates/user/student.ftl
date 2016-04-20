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
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                    </div>
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
                                    <a class="btn btn-danger" href="#">
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

<#include "include/footer.ftl">

</body>
</html>