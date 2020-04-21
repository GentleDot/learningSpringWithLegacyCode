package net.gentledot.springcodeproject.services.board;

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
            throw new RuntimeException("게시물 생성에 실패하였습니다.");
        }
    }

    @Override
    public Board read(Long bno) {
        return boardMapper.findByBno(bno)
                .orElseThrow(() -> new RuntimeException("해당 ID의 게시물을 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public void modify(Board board) {
        Integer result = boardMapper.update(board);
        if (result != 1) {
            throw new RuntimeException("게시물 수정에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public void remove(Long bno) {
        Integer result = boardMapper.delete(bno);
        if (result != 1) {
            throw new RuntimeException("게시물 삭제에 실패하였습니다.");
        }
    }

    @Override
    public List<Board> listAll() {
        return boardMapper.findAll();
    }
}
