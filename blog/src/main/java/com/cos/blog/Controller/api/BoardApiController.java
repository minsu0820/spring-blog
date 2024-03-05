package com.cos.blog.Controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.Service.BoardService;
import com.cos.blog.model.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    final private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> upload(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {

        boardService.업로드(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteBoard(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail principal) {

        if (principal.getUser().getId() == boardService.find(id).getUsers().getId()) {
            boardService.삭제(id);
        }

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/update")
    public ResponseDto<Integer> update(@RequestBody Board board) {
        boardService.update(board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
