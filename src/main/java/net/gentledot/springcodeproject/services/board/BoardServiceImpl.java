package net.gentledot.springcodeproject.services.board;

import net.gentledot.springcodeproject.errors.TargetNotFoundException;
import net.gentledot.springcodeproject.errors.TransactionFailException;
import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.repository.member.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Transactional
    @Override
    public void regist(Board board) {
        Integer result = boardMapper.create(board);
        if (result != 1) {
            throw new TransactionFailException("저장에 실패하였습니다.", Board.class);
        }
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
}
