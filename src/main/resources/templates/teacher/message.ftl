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
                <li><a href="#">消息</a></li>
            </ul>

            <div class="row-fluid">

                <div class="span12">
                    <h1>消息列表</h1>
                    <#if message??>
                        <#if (message?size > 0)>
                            <ul class="messagesList">
                            <#list message as m>
                            <#if m.is_read == "0">
                                <li>
                                    <span class="from"><a class="glyphicons star"><i></i></a><strong>${m.s_id}&nbsp;&nbsp;${m.s_name}&nbsp;&nbsp;${m.date}</strong></span>
                                    <span class="title"><strong>${m.e_name}&nbsp;&nbsp;${m.text}</strong></span>
                                    <a class="btn btn-mini btn-success" data-toggle="modal" data-target="#detailModal"
                                       data-mid="${m.id}" data-sname="${m.s_name}" data-ename="${m.e_name}" data-text="${m.text}"
                                       data-sid="${m.s_id}" data-date="${m.date}" data-read="${m.is_read}">查看</a>
                                </li>
                            <#else>
                                <li>
                                    <span class="from"><a class="glyphicons dislikes"><i></i></a>${m.s_id}&nbsp;&nbsp;${m.s_name}&nbsp;&nbsp;${m.date}</span>
                                    <span class="title">${m.e_name}&nbsp;&nbsp;${m.text}</span>
                                    <a class="btn btn-mini btn-success" data-toggle="modal" data-target="#detailModal"
                                       data-mid="${m.id}" data-sname="${m.s_name}" data-ename="${m.e_name}" data-text="${m.text}"
                                       data-read="${m.is_read}">查看</a>
                                </li>
                            </#if>
                            </#list>
                            </ul>
                        <#else>
                            <h2>暂无消息</h2>
                        </#if>
                    </#if>
                </div>
            </div>

        </div>
    </div>

</div>
</div>

<div class="modal hide fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3 class="modal-title"></h3>
    </div>
    <div class="modal-body">

    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="confirm">确定</button>
    </div>
</div>

<#include "include/footer.ftl">

<script type="text/javascript">
    var m_id = "";
    var s_id = "";
    var s_name = "";
    var e_name = "";
    var text = "";
    var date = "";
    var isRead = "";
    $("#detailModal").on("show.bs.modal", function (event) {
        var button = $(event.relatedTarget);
        m_id = button.data("mid");
        s_id = button.data("sid");
        s_name = button.data("sname");
        e_name = button.data("ename");
        text = button.data("text");
        date = button.data("date");
        isRead = button.data("read");
        var modal = $(this);
        modal.find('.modal-title').text(s_name + " " + e_name);
        modal.find('.modal-body').text(text);
    });

    $('#confirm').click(function(){
        if(isRead == "1"){
            $('#detailModal').modal('hide');
        } else {
            $.ajax({
                url: '${base}/teacher/teacher/readMessage',
                type: 'POST',
                data: $.param({"id": m_id}),
                success: function () {
                    location.reload();
                }
            });
        }
    });
</script>

</body>
</html>