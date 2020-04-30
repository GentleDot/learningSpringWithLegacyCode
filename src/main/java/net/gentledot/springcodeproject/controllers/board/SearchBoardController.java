package net.gentledot.springcodeproject.controllers.board;

import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.model.board.PageCriteria;
import net.gentledot.springcodeproject.model.board.PageSearchCriteria;
import net.gentledot.springcodeproject.services.board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
    private static final String SUCCESS_FLAG = "SUCCESS";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BoardService boardService;

    public SearchBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/list")
    public void listpage(@ModelAttribute("criteria") PageSearchCriteria criteria,
                         Model model) {
        log.info("기준 확인 : {}", criteria);
        Map<String, Object> boardMap = boardService.listSearchCriteria(criteria);

        model.addAttribute("list", boardMap.get("list"));
        model.addAttribute("pageMaker", boardMap.get("pageMaker"));
    }

    @GetMapping(value = "/readPage")
    public void listpage(@RequestParam("bno") Long boardNo,
                         @ModelAttribute("criteria") PageSearchCriteria criteria,
                         Model model) {
        log.info("게시물 조회 페이지 요청.");
        model.addAttribute(boardService.read(boardNo));
    }

    @DeleteMapping(value = "/removePage")
    public String removeBoardWithPageInfo(@RequestParam("bno") Long boardNo,
                                          PageSearchCriteria criteria,
                                          RedirectAttributes redirectAttr) {
        boardService.remove(boardNo);

        redirectAttr.addAttribute("page", criteria.getPage());
        redirectAttr.addAttribute("perPageNum", criteria.getPerPageNum());
        redirectAttr.addAttribute("searchType", criteria.getSearchType());
        redirectAttr.addAttribute("keyword", criteria.getKeyword());
        redirectAttr.addFlashAttribute("result", SUCCESS_FLAG);

        return "redirect:/sboard/list";
    }

    @GetMapping(value = "/modifyPage")
    public void boardModifierViewWithPageInfo(@RequestParam("bno") Long boardNo,
                                              @ModelAttribute("criteria") PageSearchCriteria criteria,
                                              Model model) {
        model.addAttribute(boardService.read(boardNo));
    }

    @PutMapping(value = "/modifyPage")
    public String modifyBoardWithPageInfo(Board board,
                                          PageSearchCriteria criteria,
                                          RedirectAttributes redirectAttr) {
        log.info("포스트 수정...");

        boardService.modify(board);

        redirectAttr.addAttribute("page", criteria.getPage());
        redirectAttr.addAttribute("perPageNum", criteria.getPerPageNum());
        redirectAttr.addAttribute("searchType", criteria.getSearchType());
        redirectAttr.addAttribute("keyword", criteria.getKeyword());
        redirectAttr.addFlashAttribute("result", SUCCESS_FLAG);

        return "redirect:/sboard/list";
    }

    @GetMapping(value = "/register")
    public void boardRegisterView(Model model) {
        log.info("게시물 등록 페이지 요청...");
    }

    @PostMapping(value = "/register")
    public String registBoard(Board board, RedirectAttributes redirectAttr) {
        log.info("게시물 등록 요청...");
        log.info("요청 게시물 : {}", board);

        boardService.regist(board);
        redirectAttr.addFlashAttribute("result", SUCCESS_FLAG);

        return "redirect:/sboard/list";
    }
}
