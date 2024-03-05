package com.cos.blog.Service;


import com.cos.blog.Repository.BoardRepository;
import com.cos.blog.model.Users;
import com.cos.blog.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    final private BoardRepository boardRepository;

    @Transactional
    public void 업로드(Board board, Users user) {
        board.setCount(0);
        board.setUsers(user);
        boardRepository.save(board);
    }

    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board find(int id) {
        return boardRepository.findById(id).get();
    }
    @Transactional
    public void 삭제(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Board board) {
        Board findBoard = boardRepository.findById(board.getId()).get();
        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());

    }
}
