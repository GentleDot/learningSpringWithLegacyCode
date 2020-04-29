package net.gentledot.springcodeproject.controllers.board;

import net.gentledot.springcodeproject.model.board.PageSearchCriteria;
import net.gentledot.springcodeproject.services.board.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BoardService boardService;

    public SearchBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/list")
    public void listpage(@ModelAttribute("criteria") PageSearchCriteria criteria,
                         Model model) {
        log.info("기준 확인 : {}", criteria);
        Map<String, Object> boardMap = boardService.listAllWithPagination(criteria);

        model.addAttribute("list", boardMap.get("list"));
        model.addAttribute("pageMaker", boardMap.get("pageMaker"));
    }
}
