<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp"%>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class='box'>
				<div class="box-header with-border">
					<h3 class="box-title">Board List</h3>
				</div>
				<div class='box-body'>
					<select name="searchType">
						<option value="n"
								<c:out value="${criteria.searchType == null?'selected':''}"/>>
							---</option>
						<option value="t"
								<c:out value="${criteria.searchType eq 't'?'selected':''}"/>>
							Title</option>
						<option value="c"
								<c:out value="${criteria.searchType eq 'c'?'selected':''}"/>>
							Content</option>
						<option value="w"
								<c:out value="${criteria.searchType eq 'w'?'selected':''}"/>>
							Writer</option>
						<option value="tc"
								<c:out value="${criteria.searchType eq 'tc'?'selected':''}"/>>
							Title OR Content</option>
						<option value="cw"
								<c:out value="${criteria.searchType eq 'cw'?'selected':''}"/>>
							Content OR Writer</option>
						<option value="tcw"
								<c:out value="${criteria.searchType eq 'tcw'?'selected':''}"/>>
							Title OR Content OR Writer</option>
					</select> <input type="text" name='keyword' id="keywordInput"
									 value='${criteria.keyword }'>
					<button id='searchBtn'>Search</button>
					<button id='newBtn'>New Board</button>
				</div>
			</div>
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LIST PAGING</h3>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th style="width: 10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px">VIEWCNT</th>
						</tr>

						<c:forEach items="${list}" var="board">
							<fmt:parseDate  value="${board.regdate}"  type="date" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedRegdate" />

							<tr>
								<td>${board.bno}</td>
								<td><a
									href='/sboard/readPage${pageMaker.makeSearch(pageMaker.criteria.page) }&bno=${board.bno}'>
										${board.title}</a></td>
								<td>${board.writer}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
										value="${parsedRegdate}" /></td>
								<td><span class="badge bg-red">${board.viewcnt }</span></td>
							</tr>

						</c:forEach>

					</table>
				</div>
				<!-- /.box-body -->


				<div class="box-footer">
                    <div class="text-center">
                        <ul class="pagination">

                            <c:if test="${pageMaker.prev}">
                                <li><a
                                        href="list${pageMaker.makeSearch(pageMaker.startPage - 1) }">&laquo;</a></li>
                            </c:if>

                            <c:forEach begin="${pageMaker.startPage }"
                                       end="${pageMaker.endPage }" var="idx">
                                <li
                                        <c:out value="${pageMaker.criteria.page == idx?'class =active':''}"/>>
                                    <a href="list${pageMaker.makeSearch(idx)}">${idx}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${pageMaker.next && pageMaker.endPage > 0}">
                                <li><a
                                        href="list${pageMaker.makeSearch(pageMaker.endPage +1) }">&raquo;</a></li>
                            </c:if>

                        </ul>
                    </div>
				</div>
				<!-- /.box-footer-->
			</div>
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->

<script>
	var result = '${result}';

	if(result === 'SUCCESS'){
		alert("처리가 완료되었습니다.");
	}

	$(function(){
		$('#searchBtn').on("click",
				function(event) {
					str = "list"
							+ '${pageMaker.makeQuery(1)}'
							+ "&searchType="
							+ $("select option:selected").val()
							+ "&keyword=" + encodeURIComponent($('#keywordInput').val());

					console.log(str);

					self.location = str;
				});

		$('#newBtn').on("click", function(evt) {
			self.location = "register";
		});
	});

</script>
<%@include file="../include/footer.jsp"%>
