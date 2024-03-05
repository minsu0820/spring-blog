let index = {
    init: function () {
        $("#btn-upload").on("click", () => {
            this.upload();
        });
        $("#btn-delete").on("click", () => {
            this.deleteBoard();
        });
        $("#btn-update").on("click", () => {
            this.updateBoard();
        });
        $("#btn-comment").on("click", () => {
            this.saveComment();
        });

        $("#btn-comment-delete").on("click", () => {
            this.deleteComment();
        });
    },
    upload: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            // dataType: "json"
        }).then(
            function (response) {
                alert("게시물 등록 완료");
                location.href="/"
            },
            function (error) {
                console.log(JSON.stringify(error));
            });
    },
    deleteBoard: function () {
        let id = $("#id").text();
        $.ajax({
            type: "DELETE",
            url: "/api/board/" + id, // URL 수정t
            contentType: "application/json; charset=utf-8",
            // dataType: "json"
        }).then(
            function (response) {
                alert("삭제 완료");
                location.href = "/";
            },
            function (error) {
                console.log(JSON.stringify(error));
            });
    },
    updateBoard: function () {
        let data={
            title: $("#title").val(),
            content: $("#content").val(),
            id: $("#id").val(),
        }
        // let board = $("#board_id").val();
        $.ajax({
            type: "PUT",
            url: "/api/board/update", // URL 수정
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            // dataType: "json"
        }).then(
            function (response) {
                alert("수정완료");
                location.href = "/";
            },
            function (error) {
                console.log(JSON.stringify(error));
            });
    },
    saveComment: function () {
        let data = {
            content: $("#comment").val()
        };
        let board = $("#id").text()

        $.ajax({
            type: "POST",
            url: `/api/board/comment/${board}`, // URL 수정
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            // dataType: "json"
        }).then(
            function (response) {
                alert("댓글 등록 완료");
                // alert(data.board,data);
                // alert(data.user);
                location.href = `/board/${board}`
            },
            function (error) {
                console.log(JSON.stringify(error));
            });
    },
    deleteComment: function () {
        let data = {
            id: $("#reply").val()
        };
        let board = $("#id").text()

        $.ajax({
            type: "DELETE",
            url: "/api/board/comment/delete", // URL 수정
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            // dataType: "json"
        }).then(
            function (response) {
                alert("댓글 삭제 완료");
                location.href = `/board/${board}`
            },
            function (error) {
                console.log(JSON.stringify(error));
            });
    }
}

index.init();

