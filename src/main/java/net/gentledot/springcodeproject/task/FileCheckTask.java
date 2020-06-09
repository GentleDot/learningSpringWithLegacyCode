package net.gentledot.springcodeproject.task;

import net.gentledot.springcodeproject.model.board.AttachFile;
import net.gentledot.springcodeproject.repository.board.BoardAttachMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FileCheckTask {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String saveLocation = "C:\\upload\\";

    private final BoardAttachMapper boardAttachMapper;

    public FileCheckTask(BoardAttachMapper boardAttachMapper) {
        this.boardAttachMapper = boardAttachMapper;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void checkFiles() {
        log.debug("file check Task Running!......");
        log.debug("today is : {}", LocalDate.now());

        List<Path> fileListPaths = new ArrayList<>();

        log.debug("====== DB 기록 확인 ======");
        List<AttachFile> oldfiles = boardAttachMapper.getOldfilesFromYesterday();
        log.debug("DB에 기록된 List : {}", oldfiles);

        if (oldfiles.isEmpty()) {
            log.debug("어제 처리된 첨부파일 없음.");
        } else {
            // DB 상에 어제 날짜로 기록된 파일 확인
            oldfiles.forEach(attachFile -> {
                StringBuilder builder = new StringBuilder();
                String replaceUploadPath = attachFile.getUploadPath().replace("/", File.separator);
                String filePath = builder.append(saveLocation)
                        .append(replaceUploadPath)
                        .append("\\")
                        .append(attachFile.getUuid())
                        .append("_")
                        .append(attachFile.getFileName())
                        .toString();
                fileListPaths.add(Paths.get(filePath));

                if (attachFile.getFileType().equals("image")) {
                    String thumbnailPath = filePath.replace(attachFile.getUuid(), "s_" + attachFile.getUuid());
                    fileListPaths.add(Paths.get(thumbnailPath));
                }
            });
        }
        log.debug("DB 기록을 확인하여 분류한 List : {}", fileListPaths);

        // 어제 날짜 폴더 확인
        File targetDir = Paths.get(saveLocation, getFolderYesterday()).toFile();

        // DB에 기록되지 않은 파일 확인
        File[] removeTargetFiles = targetDir.listFiles(pathname -> !fileListPaths.contains(pathname.toPath()));

        // 대상 파일 삭제
        log.debug("====== 파일 삭제 작업 ======");
        log.debug("대상 파일 List : {}", Arrays.toString(removeTargetFiles));

        Arrays.stream(removeTargetFiles).forEach(File::delete);
    }

    private String getFolderYesterday() {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        return yesterday.toString().replace("-", File.separator);
    }
}
