package com.cos.blog.Controller.api;


import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Reply;
import com.cos.blog.Service.BoardService;
import com.cos.blog.Service.ReplyService;
import com.cos.blog.config.auth.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    final private BoardService boardService;

    final private ReplyService replyService;

    @PostMapping("/api/board/comment/{id}")
    public ResponseDto<Integer> saveReply(@RequestBody Reply reply, @PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal) {

        replyService.댓글저장(reply, boardService.find(id), principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/comment/delete")
    public ResponseDto<Integer> deleteReply(@RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
        replyService.댓글삭제(reply, principal);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
