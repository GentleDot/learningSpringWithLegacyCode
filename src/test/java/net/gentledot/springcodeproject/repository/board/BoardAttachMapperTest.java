package net.gentledot.springcodeproject.repository.board;

import net.gentledot.springcodeproject.model.board.AttachFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardAttachMapperTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardAttachMapper boardAttachMapper;

    @Test
    @DisplayName("게시물 첨부파일 등록")
    void insertTest() {
        AttachFile attachFile = new AttachFile();
        attachFile.setUuid("test1234");
        attachFile.setFileName("testName");
        attachFile.setFileType("image");
        attachFile.setUploadPath("temp/test");

        Integer insert = boardAttachMapper.insert(attachFile);

        assertThat(insert, is(1));
    }

    @Test
    @DisplayName("게시물 첨부파일 삭제")
    void deleteTest() {
        Integer delete = boardAttachMapper.delete("test");

        assertThat(delete, is(1));
    }

    @Test
    @DisplayName("게시물에 첨부된 모든 첨부파일 조회")
    void findAllByBnoTest() {
        List<AttachFile> attachFileList = boardAttachMapper.findAllByBno(1L);
        log.debug("1번 게시물의 첨부파일 리스트 : {}", attachFileList);

        assertThat(attachFileList, is(notNullValue()));
    }

    @Test
    @DisplayName("게시물에 첨부된 모든 첨부파일 삭제")
    void delteAllByBnoTest() {
        Integer result = boardAttachMapper.delteAllByBno(1L);

        assertThat(result, is(1)); // PreparedStatement.getUpdateCount(), 즉 처리 row 수를 return
    }
}