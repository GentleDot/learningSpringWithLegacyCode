package net.gentledot.springcodeproject.controllers.reply;

import net.gentledot.springcodeproject.model.reply.Reply;
import net.gentledot.springcodeproject.services.reply.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/replies")
@RestController
public class ReplyController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String SUCCESS_MESSAGE = "success";

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public ResponseEntity<String> registReply(@RequestBody Reply reply) {
        log.info("요청 댓글 : {}", reply);
        int result = replyService.register(reply);

        return result == 1
                ? new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/board/{bno}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Reply>> getRepliesInBoard(@PathVariable("bno") Long boardNo) {
        log.info("게시물의 댓글 목록 : {} 번 게시물", boardNo);

        return new ResponseEntity<>(replyService.findAllByBno(boardNo), HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
            value = "/{rno}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public ResponseEntity<String> modifyReply(@PathVariable("rno") Long replyNo,
                                              @RequestBody Reply reply) {
        log.info("수정 대상 댓글 : {} 번, 수정 내용 : {}", replyNo, reply);
        int result = replyService.modify(replyNo, reply);

        return result == 1
                ? new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{rno}",
            produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public ResponseEntity<String> removeReply(@PathVariable("rno") Long replyNo) {
        log.info("삭제 대상 댓글 : {} 번", replyNo);
        int result = replyService.remove(replyNo);

        return result == 1
                ? new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
