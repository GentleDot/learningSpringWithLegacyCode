package net.gentledot.springcodeproject.services.board;

import net.gentledot.springcodeproject.model.PageCriteria;
import net.gentledot.springcodeproject.model.board.Board;

import java.util.List;

public interface BoardService {
    void regist(Board board);
    Board read(Long bno);
    void modify(Board board);
    void remove(Long bno);
    List<Board> listAll();
    List<Board> listAllWithPagination(PageCriteria criteria);
}
