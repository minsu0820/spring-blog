package com.cos.blog.Controller;


import com.cos.blog.Service.BoardService;
import com.cos.blog.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    final private BoardService boardService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 3, sort = "createDate",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boards = boardService.글목록(pageable);
        int totalPages = boards.getTotalPages();
        //10개이하
        int startPage = ((pageable.getPageNumber() - 1) / 10) * 10 + 1;
        int endPage = startPage + 10 - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "index";
    }

    @GetMapping("board/saveForm")
    public String SaveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String boardInfor(@PathVariable int id, Model model) {
        Board board = boardService.find(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.find(id));
        return "board/updateForm";
    }

//    @PostMapping("board/upload")
//    public String upload(@RequestBody )
}
