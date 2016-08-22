<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

    <!-- start: Content -->
    <div id="content" class="span10">
        <ul class="breadcrumb">
            <li>
                <i class="icon-home"></i>
                <a href="${base}/student/home">首页</a>
                <i class="icon-angle-right"></i>
            </li>
            <li><a href="#">概览</a></li>
        </ul>

        <div class="row-fluid">
            <div class="span6">
                <div class="page-header">
                    <h1>系统下载</h1>
                    <blockquote>
                        <p>NS2实验环境下载：<a href=${cloud_url}>百度网盘下载</a></p>
                        <p>WIFI攻防实验环境下载：<a href="${base}/file/Kali2.0安装指南.pdf">Kali2.0安装指南.pdf</a></p>
                    </blockquote>
                </div>
            </div>
            <div class="span6">
                <div class="page-header">
                    <h1>基本信息</h1>
                    <blockquote>
                        <p>报告终稿：${student.report_status}</p>
                        <p>最终成绩：<#if (student.s_score?number == 0)>未公布<#else>${student.s_score}</#if></p>
                    </blockquote>
                </div>
            </div>
        </div>

        <div class="row-fluid">

            <div class="span12">
                <h1>待完成实验</h1>

            <#if (activeExp?size > 0)>
                <#list activeExp as aExp>
                    <#if aExp.status == "+">

                    <div class="task low">
                        <div class="desc">
                            <div class="title">${aExp.e_name}</div>
                            <div>${aExp.e_description}</div>
                        </div>
                        <#if aExp.ref_path == "">
                            <div class="time">
                                <span class="label label-warning">暂无参考代码</span>
                            </div>
                        <#else>
                            <div class="time">
                                <span class="label label-success">参考代码可下载</span>
                                <div> <a href="${base}/file/ref/${aExp.ref_path}">下载</a></div>
                            </div>
                        </#if>
                    </div>

                    <#else>

                    <div class="task high">
                        <div class="desc">
                            <div class="title">${aExp.e_name}</div>
                            <div>${aExp.e_description}</div>
                        </div>
                        <div class="time">
                            <span class="label label-important">参考代码未开放</span>
                        </div>
                    </div>

                    </#if>
                </#list>
            </#if>

            </div>
        </div>
    </div>

    </div>
</div>

<#include "include/footer.ftl">

</body>
</html>