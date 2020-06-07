package net.gentledot.springcodeproject.services.board;

import net.gentledot.springcodeproject.model.board.PageCriteria;
import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.model.board.PageSearchCriteria;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void regist(Board board);
    Board read(Long bno);
    void modify(Board board);
    void remove(Long bno);
    List<Board> listAll();
    Map<String, Object> listAllWithPagination(PageCriteria criteria);
    Map<String, Object> listSearchCriteria(PageSearchCriteria criteria);


}
