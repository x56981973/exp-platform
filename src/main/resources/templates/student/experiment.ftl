<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#include "include/menu.ftl">

        <!-- start: Content -->
        <div id="content" class="span10">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="${base}/student/home">主页</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="#">实验列表</a></li>
            </ul>

        <#if (expClassList?size > 0)>
            <#list expClassList as eClass>

            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header">
                        <h2><i class="halflings-icon list"></i><span class="break"></span>${eClass.class_name}</h2>
                        <div class="box-icon">
                            <a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>
                        </div>
                    </div>
                    <div class="box-content">
                        <ul>
                            <#if (expTypeList?size > 0)>
                                <#list expTypeList as eType>
                                    <#if (eType.class_id?number == eClass.class_id)>

                                        <li>${eType.type_name}</li>
                                            <ul>

                                        <#if (experimentList?size > 0)>
                                            <#list experimentList as exp>
                                                <#if (exp.class_id?number == eClass.class_id && exp.type_id?number == eType.type_id)>

                                                    <li> <a href="${base}/file/exp/${exp.e_srcPath}">${exp.e_name}</a></li>

                                                </#if>
                                            </#list>
                                        </#if>

                                            </ul>
                                    </#if>
                                </#list>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>

            </#list>
        </#if>

        </div>

    </div>
</div>

<#include "include/footer.ftl">

</body>
</html>