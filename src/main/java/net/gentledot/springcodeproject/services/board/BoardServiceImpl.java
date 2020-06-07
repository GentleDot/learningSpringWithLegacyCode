package net.gentledot.springcodeproject.services.board;

import net.gentledot.springcodeproject.errors.TargetNotFoundException;
import net.gentledot.springcodeproject.errors.TransactionFailException;
import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.model.board.PageCriteria;
import net.gentledot.springcodeproject.model.board.PageMaker;
import net.gentledot.springcodeproject.model.board.PageSearchCriteria;
import net.gentledot.springcodeproject.repository.board.BoardAttachMapper;
import net.gentledot.springcodeproject.repository.board.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final BoardAttachMapper boardAttachMapper;

    public BoardServiceImpl(BoardMapper boardMapper, BoardAttachMapper boardAttachMapper) {
        this.boardMapper = boardMapper;
        this.boardAttachMapper = boardAttachMapper;
    }

    @Transactional
    @Override
    public void regist(Board board) {
        Integer result = boardMapper.create(board);
        if (result != 1) {
            throw new TransactionFailException("저장에 실패하였습니다.", Board.class);
        }

        if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
            return;
        }

        board.getAttachList().forEach(attachFile -> {
            attachFile.setBno(board.getBno());
            boardAttachMapper.insert(attachFile);
        });
    }

    @Override
    public Board read(Long bno) {
        return boardMapper.findByBno(bno)
                .orElseThrow(() -> new TargetNotFoundException(bno, Board.class));
    }

    @Override
    @Transactional
    public void modify(Board board) {
        Integer result = boardMapper.update(board);
        if (result != 1) {
            throw new TransactionFailException("수정에 실패하였습니다.", Board.class);
        }
    }

    @Override
    @Transactional
    public void remove(Long bno) {
        Integer result = boardMapper.delete(bno);
        if (result != 1) {
            throw new TransactionFailException("삭제에 실패하였습니다.", Board.class);
        }
    }

    @Override
    public List<Board> listAll() {
        return boardMapper.findAll();
    }

    /*
     * list = PageCriteria에서 설정한 page, page 내 보여줄 data를 기준으로 List<Board>가 담김
     * pageMaker = Paging 처리를 위한 객체가 담김 (totalCount, startPage, endPage, prev, next, displayPageNum)
     */
    @Override
    public Map<String, Object> listAllWithPagination(PageCriteria criteria) {
        HashMap<String, Object> resultMap = new HashMap<>();
        PageMaker pageMaker = new PageMaker(criteria, boardMapper.countPaging());

        resultMap.put("list", boardMapper.findAllWithPagination(criteria));
        resultMap.put("pageMaker", pageMaker);

        return resultMap;
    }

    /*
     * list = PageCriteria에서 설정한 page, page 내 보여줄 data를 기준으로 List<Board>가 담김
     * pageMaker = Paging 처리를 위한 객체가 담김 (totalCount, startPage, endPage, prev, next, displayPageNum)
     */
    @Override
    public Map<String, Object> listSearchCriteria(PageSearchCriteria criteria) {
        HashMap<String, Object> resultMap = new HashMap<>();
        PageMaker pageMaker = new PageMaker(criteria, boardMapper.countBoardListSearch(criteria));

        resultMap.put("list", boardMapper.findAllByKeyword(criteria));
        resultMap.put("pageMaker", pageMaker);

        return resultMap;
    }
}
