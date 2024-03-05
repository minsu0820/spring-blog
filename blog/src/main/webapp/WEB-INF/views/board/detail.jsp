<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="container">
            <div>
                글 번호: <span id="id"><i>${board.id}</i></span>
                작성자: <span><i>${board.users.username}</i></span>
            </div>
            <!-- 블로그 글 내용을 표시하는 부분 -->
            <article style="text-align: center;">
                <h1 style="font-size: 2.5em;">${board.title}</h1>
                <p class="meta" style="text-align: right; margin-right: 20px;">
                    작성 시간: <i>${board.createDate}</i>
                </p>
                <p style="margin-top: 30px; margin-bottom: 30px; font-size: 1.2em;">
                    ${board.content}
                </p>
            </article>

            <!-- 수정 및 삭제 버튼 -->
            <c:if test="${board.users.id eq principal.user.id}">
                <div style="display: flex; justify-content: flex-end; margin-top: 20px;">
                    <a href="/board/${board.id}/updateForm" class="btn btn-primary btn-sm">수정하기</a>
                    <button id="btn-delete" class="btn btn-danger btn-sm" onclick="return confirm('삭제하시겠습니까?')">삭제하기</button>
                </div>
            </c:if>
            <button class="btn btn-secondary" onclick="history.back()" style="display: block; margin-top: 30px;">글 목록으로 돌아가기</button>

            <!-- 댓글 섹션 등 추가 가능 -->
            <div class="card" style="margin-top: 20px;">
                <div>
                    <div class="card-body" style="display: flex; align-items: center; justify-content: space-between;">
                        <div style="flex-grow: 1;">
                            <label for="commentUser">작성자 : </label>
                            <input type="text" value="${principal.user.username}" id="commentUser" readonly style="border: none; background-color: transparent; outline: none;">
                        </div>
                        <div style="flex-grow: 2;">
                            <textarea id="comment" class="form-control" rows="1"></textarea>
                        </div>
                        <div>
                            <button id="btn-comment" class="btn btn-primary">댓글 등록</button>
                        </div>
                    </div>
                    <br/>
                    <div class ="card-header">댓글 목록</div>
                    <ul id ="comment--items" class="list-group">
                        <c:forEach var="reply" items="${board.replies}">
                            <input type="hidden" value="${reply.id}" id="reply"/>
                            <li id="comment--1" class="list-group-item d-flex justify-content-between">
                                <div>${reply.content}</div>
                                <div class="d-flex">
                                    <div class="font-italic">${reply.user.username}&nbsp;</div>
                                    <c:if test="${reply.user.id eq principal.user.id}">
                                        <button id="btn-comment-delete" class="badge">삭제</button>
                                    </c:if>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>