package net.gentledot.springcodeproject.services.reply;

import net.gentledot.springcodeproject.model.reply.Reply;
import net.gentledot.springcodeproject.repository.reply.ReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ReplyMapper replyMapper;

    public ReplyServiceImpl(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    @Override
    public int register(Reply reply) {
        log.debug("댓글 등록......");

        return replyMapper.save(reply);
    }

    @Override
    public Reply findByRno(Long rno) {
        log.debug("댓글번호로 조회......");
        return replyMapper.findByRno(rno).orElseThrow(() -> new RuntimeException("해당되는 댓글이 존재하지 않습니다."));
    }

    @Override
    public int modify(Reply reply) {
        log.debug("댓글 수정......");
        return replyMapper.update(reply);
    }

    @Override
    public int remove(Long rno) {
        log.debug("댓글 삭제......");
        return replyMapper.delete(rno);
    }

    @Override
    public List<Reply> findAllByBno(Long bno) {
        log.debug("게시물 내 댓글 전체 목록 조회......");
        return replyMapper.findAllByBno(bno);
    }
}
