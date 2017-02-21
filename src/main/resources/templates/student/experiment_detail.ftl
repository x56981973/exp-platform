<#include "include/header.ftl">

<div class="container-fluid-full">
    <div class="row-fluid">
    <#--<#include "include/menu.ftl">-->
        <div id="sidebar-left" class="span2">
            <div class="nav-collapse sidebar-nav">
                <ul class="nav nav-tabs nav-stacked main-menu">
                    <li ><a href="#" id="l1"><i class="icon-align-justify"></i><span class="hidden-tablet"> 实验目的</span></a></li>
                    <li ><a href="#" id="l2"><i class="icon-align-justify"></i><span class="hidden-tablet"> 实验内容</span></a></li>
                    <li ><a href="#" id="l3"><i class="icon-align-justify"></i><span class="hidden-tablet"> 实验步骤</span></a></li>
                    <li ><a href="#" id="l4"><i class="icon-align-justify"></i><span class="hidden-tablet"> 实验结果</span></a></li>
                </ul>
            </div>
        </div>
        <!-- start: Content -->
        <div id="content" class="span10">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="${base}/student/home">主页</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>
                    <a href="${base}/student/exp">实验列表</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li><a href="#">${experiment.e_name}</a></li>
            </ul>
            <#--<a href="${base}/file/exp/${exp.e_srcPath}">${exp.e_name}</a>-->
            <iframe id="iframepage" height="700"></iframe>
        </div>

    </div>
</div>

<#include "include/footer.ftl">

<script type="text/javascript">
    var path = "${experiment.e_srcPath}";
    var len = path.length-4;
    var route = path.substring(0, len);

    var w = $('#content').width();
    $('#iframepage').css("width",w);
    $('#iframepage').attr("src","${base}/file/exp_detail/"+ route +"/实验目的.pdf");
</script>

<script type="text/javascript">
    var path = "${experiment.e_srcPath}";
    var len = path.length-4;
    var route = path.substring(0, len);

    $('#l1').click(function(){
        $('#iframepage').attr("src","${base}/file/exp_detail/"+ route +"/实验目的.pdf");
    });
    $('#l2').click(function(){
        $('#iframepage').attr("src","${base}/file/exp_detail/"+ route +"/实验内容.pdf");
    });
    $('#l3').click(function(){
        $('#iframepage').attr("src","${base}/file/exp_detail/"+ route +"/实验步骤.pdf");
    });
    $('#l4').click(function(){
        $('#iframepage').attr("src","${base}/file/exp_detail/"+ route +"/实验结果.pdf");
    });
</script>

</body>
</html>