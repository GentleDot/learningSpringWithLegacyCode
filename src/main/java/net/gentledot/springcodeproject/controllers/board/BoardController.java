package net.gentledot.springcodeproject.controllers.board;

import net.gentledot.springcodeproject.model.board.PageCriteria;
import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.services.board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board/*")
public class BoardController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String SUCCESS_FLAG = "SUCCESS";

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/register")
    public String boardRegisterView(Model model) {
        log.info("게시물 등록 페이지 요청...");

        return "board/register";
    }

    @PostMapping(value = "/register")
    public String registBoard(Board board, RedirectAttributes redirectAttr) {
        log.info("게시물 등록 요청...");
        log.info("요청 게시물 : {}", board);

        boardService.regist(board);
        redirectAttr.addFlashAttribute("result", SUCCESS_FLAG);

        return "redirect:/board/listAll";
    }

    @GetMapping(value = "/listAll")
    public void listAllBoard(Model model) {
        log.info("전체 게시물 목록 조회.");

        List<Board> list = boardService.listAll();
        model.addAttribute("list", list);
    }

    @GetMapping(value = "/listCri")
    public void listAllBoardWithPagination(PageCriteria criteria, Model model) {
        log.info("페이징 기준이 설정된 게시물 리스트 조회");

        model.addAttribute("list", boardService.listAllWithPagination(criteria));
    }

    @GetMapping(value = "/read")
    public void readBoard(@RequestParam("bno") Long boardNo, Model model) {
        model.addAttribute(boardService.read(boardNo));
    }

    @DeleteMapping(value = "/remove")
    public String removeBoard(@RequestParam("bno") Long boardNo, RedirectAttributes redirectAttr) {
        boardService.remove(boardNo);
        redirectAttr.addFlashAttribute("result", SUCCESS_FLAG);

        return "redirect:/board/listAll";
    }

    @GetMapping(value = "/modify")
    public void boardModifierView(@RequestParam("bno") Long boardNo, Model model) {
        model.addAttribute(boardService.read(boardNo));
    }

    @PutMapping(value = "/modify")
    public String modifyBoard(Board board, RedirectAttributes redirectAttributes) {
        log.info("포스트 수정...");

        boardService.modify(board);
        redirectAttributes.addFlashAttribute("result", SUCCESS_FLAG);

        return "redirect:/board/listAll";
    }


}
