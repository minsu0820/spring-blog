<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="layout/header.jsp" %>

<div class="container">
    <c:forEach var="board" items="${boards.content}">
        <div class="card m-3">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <p class="card-text">${board.content}</p>
                <a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
            </div>
        </div>
    </c:forEach>
</div>
<ul class="pagination justify-content-center">
    <li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">이전</a></li>
    <c:forEach var="pageNumber" begin="${startPage}" end="${endPage}">
        <c:choose>
            <c:when test="${pageNumber eq boards.number+1}">
                <li class="page-item active">
                    <a class="page-link" href="?page=${pageNumber-1}">${pageNumber}</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="?page=${pageNumber-1}">${pageNumber}</a>
                </li>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">다음</a></li>
</ul>

<%@ include file="layout/footer.jsp" %>
