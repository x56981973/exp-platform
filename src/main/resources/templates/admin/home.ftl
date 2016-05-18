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
            <li><a href="#">概览</a></li>
        </ul>

        <div class="row-fluid">

            <div class="span4 statbox orange">
                <div class="boxchart">5,6,7,2,0,4,2,4,8,2,3,3,2</div>
                <div class="number">${s_num}<i class="icon-group"></i></div>
                <div class="title">学生总数</div>
                <div class="footer">
                    <a href="${base}/admin/student"> 查看学生</a>
                </div>
            </div>
            <div class="span4 statbox green">
                <div class="boxchart">1,2,6,4,0,8,2,4,5,3,1,7,5</div>
                <div class="number">${t_num}<i class="icon-user"></i></div>
                <div class="title">教师总数</div>
                <div class="footer">
                    <a href="${base}/admin/teacher"> 查看教师</a>
                </div>
            </div>
            <div class="span4 statbox blue noMargin">
                <div class="boxchart">5,6,7,2,0,-4,-2,4,8,2,3,3,2</div>
                <div class="number">${e_num}<i class="icon-book"></i></div>
                <div class="title">实验总数</div>
                <div class="footer">
                    <a href="${base}/admin/exp"> 查看实验</a>
                </div>
            </div>
        </div>

        <div class="row-fluid">

            <div class="box black span4" onTablet="span6" onDesktop="span4">
                <div class="box-header">
                    <h2><i class="halflings-icon white user"></i><span class="break"></span>登陆记录</h2>
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon white chevron-up"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <ul class="dashboard-list metro">
                        <#if (login_record?size > 0)>
                            <#list login_record as record>
                                <li>
                                    <i class="icon-arrow-up green"></i>
                                    <a>
                                        <strong>上次登录: </strong>
                                        ${record.date}
                                        <#if record.ip_address??>
                                            IP: ${record.ip_address}
                                        </#if>
                                    </a>
                                </li>
                            </#list>
                        </#if>
                    </ul>
                </div>
            </div><!--/span-->

            <div class="box black span8" onTablet="span12" onDesktop="span8">
                <div class="box-header">
                    <h2><i class="halflings-icon white list"></i><span class="break"></span>操作记录</h2>
                    <div class="box-icon">
                        <a href="#" class="btn-minimize"><i class="halflings-icon white chevron-up"></i></a>
                    </div>
                </div>
                <div class="box-content">
                    <ul class="dashboard-list metro">
                        <#if (option_record?size > 0)>
                            <#list option_record as record>
                                <#if record.option_class == "student">
                                    <li class="orange">
                                <#elseif record.option_class == "teacher">
                                    <li class="green">
                                <#elseif record.option_class == "exp">
                                    <li class="blue">
                                </#if>
                                <strong>操作时间：</strong> ${record.date}<br>
                                <strong>操作内容：</strong> ${record.option_detail}<br>
                                </li>
                            </#list>
                        </#if>
                    </ul>
                </div>
            </div><!--/span-->

        </div>

    </div>

    </div>
</div>

<#include "include/footer.ftl">