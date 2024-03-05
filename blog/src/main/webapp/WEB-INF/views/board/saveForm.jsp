<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../layout/header.jsp" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form>
                <input type="hidden" id="id" value="${board.id}">
                <div class="form-group">
                    <label for="title">제목</label>
                    <input type="text" name="title" class="form-control" placeholder="제목을 입력하세요" id="title">
                </div>
                <div class="form-group">
                    <label for="content">내용</label>
                    <textarea class="form-control summernote" rows="15" name="content" id="content"></textarea>
                </div>
            </form>
            <button id="btn-upload" class="btn btn-primary">글 등록</button>
        </div>
    </div>
</div>

<script>
    $('.summernote').summernote({
        placeholder: '내용을 입력하세요',
        tabsize: 2,
        height: 400,
    });
</script>

<script src="/js/board.js"></script>
<%@include file="../layout/footer.jsp" %>
