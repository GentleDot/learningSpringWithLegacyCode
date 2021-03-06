package net.gentledot.springcodeproject.model.board;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class Board {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regdate;
    private Long viewcnt;

    private List<AttachFile> attachList;

    public Board(Long bno, String title, String content, String writer, LocalDateTime regdate, Long viewcnt) {
        viewcnt = ObjectUtils.defaultIfNull(viewcnt, 0L);

        checkArgument(StringUtils.isNotBlank(title), "게시물 제목은 빈 값이 될 수 없습니다.");
        checkArgument(title.getBytes(StandardCharsets.UTF_8).length <= 200, "게시물 제목은 200bytes 내외로 작성 가능합니다.");
        checkArgument(StringUtils.isNotBlank(writer), "게시물 제목은 빈 값이 될 수 없습니다.");
        checkArgument(writer.getBytes(StandardCharsets.UTF_8).length <= 50, "게시물 제목은 200bytes 내외로 작성 가능합니다.");
        checkArgument(viewcnt >= 0, "view count 값은 음수를 허용하지 않습니다.");


        this.bno = ObjectUtils.defaultIfNull(bno, 0L);
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regdate = ObjectUtils.defaultIfNull(regdate, LocalDateTime.now());
        this.viewcnt = viewcnt;
    }

    public long getBno() {
        return bno;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getRegdate() {
        return regdate;
    }

    public long getViewcnt() {
        return viewcnt;
    }

    public long increaseAndGetViewcnt() {
        return ++viewcnt;
    }

    public List<AttachFile> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<AttachFile> attachList) {
        this.attachList = attachList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("bno", bno)
                .append("title", title)
                .append("content", content)
                .append("writer", writer)
                .append("regdate", regdate)
                .append("viewcnt", viewcnt)
                .append("attachList", attachList)
                .toString();
    }

    public static final class Builder {
        private Long bno;
        private String title;
        private String content;
        private String writer;
        private LocalDateTime regdate;
        private Long viewcnt;
        private List<AttachFile> attachList;

        public Builder() {
        }

        public Builder(Board board) {
            this.bno = board.bno;
            this.title = board.title;
            this.content = board.content;
            this.writer = board.writer;
            this.regdate = board.regdate;
            this.viewcnt = board.viewcnt;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder writer(String writer) {
            if (this.writer == null) {
                this.writer = writer;
            }
            return this;
        }

        public Board build() {
            return new Board(bno, title, content, writer, regdate, viewcnt);
        }
    }
}
