package com.cos.blog.Service;


import com.cos.blog.Repository.ReplyRepository;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Reply;
import com.cos.blog.model.Users;
import com.cos.blog.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {
    final private ReplyRepository replyRepository;

    @Transactional
    public void 댓글저장(Reply reply, Board board, Users user) {
        reply.setBoard(board);
        reply.setUser(user);
        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(Reply reply, PrincipalDetail principal) {
        Reply deleteReply = replyRepository.findById(reply.getId()).get();

        if (deleteReply.getUser().getId() == principal.getUser().getId()) {
            replyRepository.delete(deleteReply);
        }
    }
}
