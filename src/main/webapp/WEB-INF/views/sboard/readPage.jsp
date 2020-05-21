<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">READ BOARD</h3>
                </div>
                <!-- /.box-header -->

                <form role="form" method="post">

                    <input type='hidden' name='bno' value="${board.bno}"/>
                    <input type='hidden' name='page' value="${criteria.page}"/>
                    <input type='hidden' name='perPageNum' value="${criteria.perPageNum}"/>
                    <input type='hidden' name='searchType' value="${criteria.searchType}"/>
                    <input type='hidden' name='keyword' value="${criteria.keyword}"/>
                    <input id="requestMethod" type="hidden" name="_method" value="" disabled="disabled"/>

                </form>

                <div class="box-body">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Title</label> <input type="text"
                                                                             name='title' class="form-control"
                                                                             value="${board.title}"
                                                                             readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Content</label>
                        <textarea class="form-control" name="content" rows="3"
                                  readonly="readonly">${board.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Writer</label> <input type="text"
                                                                              name="writer" class="form-control"
                                                                              value="${board.writer}"
                                                                              readonly="readonly">
                    </div>
                </div>
                <!-- /.box-body -->

                <div class="box-footer">
                    <button type="submit" id="boardBtnMod" class="btn btn-warning">Modify</button>
                    <button type="submit" id="boardBtnRem" class="btn btn-danger">REMOVE</button>
                    <button type="submit" id="boardBtnList" class="btn btn-primary">LIST ALL</button>
                </div>

            </div>
            <!-- /.box -->
        </div>
        <!--/.col (left) -->

    </div>
    <!-- /.row -->

    <div class="row">
        <div class="col-md-12">

            <div class="box box-success">
                <div class="box-header">
                    <h3 class="box-title">ADD NEW REPLY</h3>
                </div>
                <div class="box-body">
                    <label for="exampleInputEmail1">Writer</label> <input
                        class="form-control" type="text" placeholder="USER ID"
                        id="newReplyWriter"> <label for="exampleInputEmail1">Reply
                    Text</label> <input class="form-control" type="text"
                                        placeholder="REPLY TEXT" id="newReplyText">

                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button type="button" class="btn btn-primary" id="replyAddBtn">ADD
                        REPLY
                    </button>
                </div>
            </div>


            <!-- timeline time label -->
            <div class="time-label" id="repliesDiv">
                <span class="bg-green"> 댓글 <small id='replycntSmall'> [] </small> </span>

                <!-- The time line -->
                <ul class="timeline">

                </ul>
            </div>

            <div class='text-center'>
                <ul id="pagination" class="pagination pagination-sm no-margin ">

                </ul>
            </div>

        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->


    <!-- Modal -->
    <div id="modifyModal" class="modal modal-primary fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">댓글 수정, 삭제</h4>
                </div>
                <div class="modal-body" data-rno="" data-replyer="">
                    <label for="replytext">댓글 : </label>
                    <p><input type="text" id="replytext" class="form-control"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
                    <button type="button" class="btn btn-danger" id="replyDelBtn">DELETE</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>


</section>
<!-- /.content -->

<script type="text/javascript" src="/resources/dist/js/reply.js"></script>

<script>
    var service = new ReplyService();
    var boardNo = ${board.bno};


    $(document).ready(function () {

        var formObj = $("form[role='form']");
        var httpMethod = $("#requestMethod");

        console.log(formObj);

        printReplyList();

        $("#boardBtnMod").on("click", function () {
            formObj.attr("action", "/sboard/modifyPage");
            formObj.attr("method", "get");
            formObj.submit();
        });

        $("#boardBtnRem").on("click", function () {
            formObj.attr("action", "/sboard/removePage");
            httpMethod.attr("value", "delete");
            httpMethod.removeAttr("disabled");
            formObj.submit();
        });

        $("#boardBtnList").on("click", function () {
            formObj.attr("action", "/sboard/list");
            formObj.attr("method", "get");
            formObj.submit();
        });

        $("#replyAddBtn").on("click", function () {
            let reply = {
                replytext: document.getElementById("newReplyText").value,
                replyer: document.getElementById("newReplyWriter").value,
                bno: boardNo
            };

            service.add(reply,
                function (result) {
                    console.log("add result : " + result);
                    /*document.getElementById("newReplyWriter").value = "";
                    document.getElementById("newReplyText").value = "";*/
                    $("#newReplyWriter").val("");
                    $("#newReplyText").val("");
                    printReplyList();
                });
        });

        $(".timeline").on("click", ".replyLi", function () {
            let reply = $(this);

            $(".modal-title").html("No : " + reply.attr("data-rno") + ", writer : " + reply.attr("data-replyer"));
            $("#replytext").val(reply.find('.timeline-body').text());
            $(".modal-body").attr("data-rno", reply.attr("data-rno"))
                .attr("data-replyer", reply.attr("data-replyer"));
        });

        $("#replyDelBtn").on("click", function () {
            let rno = $(".modal-body").attr("data-rno");

            service.remove(rno, function (result) {
                console.log("remove 결과 : " + result);
                if (result) {
                    // $("#modifyModal").css({'display' : 'none'});
                    $("#modifyModal").modal('toggle');
                    printReplyList();
                }

            })
        });

        $("#replyModBtn").on("click", function () {
            let replyNo = $(".modal-body").attr("data-rno");
            let replyer = $(".modal-body").attr("data-replyer");
            let replytext = $("#replytext").val();

            let reply = {
                bno : boardNo,
                rno : replyNo,
                replyer : replyer,
                replytext : replytext
            };

            service.update(reply, function(result){
                console.log("update 결과 : " + result);
                if (result) {
                    $("#modifyModal").modal('toggle');
                    printReplyList();
                }
            })

        });

        function printReplyList() {
            service.getList(boardNo, function (list) {
                let length = list.length || 0;
                console.log("댓글 수 : " + length);
                console.log("list_index 0 확인 : " + JSON.stringify(list[0]));


                $("#replycntSmall").html("[ " + length + " ]");
                let replyLi = "";

                $(list).each(function () {
                    replyLi += "<li class=\"replyLi\" data-rno=\"" + this.rno + "\" data-replyer=\"" + this.replyer + "\">\n" +
                        "    <i class=\"fa fa-comments bg-blue\"></i>\n" +
                        "    <div class=\"timeline-item\">\n" +
                        "        <span class=\"time\"><i class=\"fa fa-clock-o\"></i> " + this.regdate + "</span>\n" +
                        "        <h3 class=\"timeline-header\"><strong> &lt" + this.rno + "&gt " + this.replyer + "</strong></h3>\n" +
                        "        <div class=\"timeline-body\"> " + this.replytext + "</div>\n" +
                        "        <div class=\"timeline-footer\">\n" +
                        "            <a class=\"btn btn-primary btn-xs\" data-toggle=\"modal\" data-target=\"#modifyModal\">수정</a>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</li>";
                });

                $(".timeline").html(replyLi);
            });
        }

    });

</script>

<%@include file="../include/footer.jsp" %>