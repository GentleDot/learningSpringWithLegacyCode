package net.gentledot.springcodeproject.services.board;

import net.gentledot.springcodeproject.errors.TargetNotFoundException;
import net.gentledot.springcodeproject.errors.TransactionFailException;
import net.gentledot.springcodeproject.model.board.*;
import net.gentledot.springcodeproject.repository.board.BoardAttachMapper;
import net.gentledot.springcodeproject.repository.board.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        if (result < 1) {
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
        if (result < 1) {
            throw new TransactionFailException("수정에 실패하였습니다.", Board.class);
        }

        boardAttachMapper.delteAllByBno(board.getBno());

        List<AttachFile> attachList = board.getAttachList();
        if (attachList.size() > 0) {
            attachList.forEach(attachFile -> {
                attachFile.setBno(board.getBno());
                boardAttachMapper.insert(attachFile);
            });
        }
    }

    @Override
    @Transactional
    public void remove(Long bno) {
        Integer result = boardMapper.delete(bno);
        if (result < 1) {
            throw new TransactionFailException("삭제에 실패하였습니다.", Board.class);
        }

        result = boardAttachMapper.delteAllByBno(bno);
        if (result < 1) {
            deleteAttachFiles(bno);
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

    @Override
    public List<AttachFile> getAttachList(Long bno) {
        log.info("게시물 내 첨부파일 조회");

        return boardAttachMapper.findAllByBno(bno);
    }


    private void deleteAttachFiles(Long bno) {
        String saveLocation = "C:\\upload\\";
        List<AttachFile> attachList = getAttachList(bno);

        if (attachList == null || attachList.size() <= 0) {
            return;
        }

        log.info("첨부파일 제거 작업 진행");
        log.info("첨부파일 List : {}", attachList);

        attachList.forEach(attachFile -> {
            try {
                Path filePath = Paths.get(saveLocation + attachFile.getUploadPath()
                        + "\\" + attachFile.getUuid() + "_" + attachFile.getFileName());
                Files.deleteIfExists(filePath);


                if (attachFile.getFileType().equals("image")) {
                    Path thumbNailPath = Paths.get(saveLocation + attachFile.getUploadPath()
                            + "\\s_" + attachFile.getUuid() + "_" + attachFile.getFileName());

                    Files.delete(thumbNailPath);
                }
            } catch (IOException e) {
                log.error("첨부파일 제거 작업에 오류가 발생하였습니다.", e);
            }
        });
    }
}
