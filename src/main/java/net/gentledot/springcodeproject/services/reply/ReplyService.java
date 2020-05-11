package net.gentledot.springcodeproject.services.reply;

import net.gentledot.springcodeproject.model.reply.Reply;

import java.util.List;

public interface ReplyService {
    int register(Reply reply);

    Reply findByRno(Long rno);

    int modify(Reply reply);

    int remove(Long rno);

    List<Reply> findAllByBno(Long bno);
}
