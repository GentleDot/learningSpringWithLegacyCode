package net.gentledot.springcodeproject.controllers.uploadSample;

import net.coobird.thumbnailator.Thumbnailator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class UploadController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String saveLocation = "C:\\upload";

    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("upload form 동작......");
    }

    @PostMapping("/uploadFormAction")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
        for (MultipartFile file : uploadFile) {
            log.info("======");
            log.info("upload file name : {}", file.getOriginalFilename());
            log.info("upload file size : {}", file.getSize());
            log.info("upload file contentType : {}", file.getContentType());
            log.info("======");

            File saveFile = new File(saveLocation, file.getOriginalFilename());

            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                log.error("파일 저장에서 오류 발생", e);
            }
        }
    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
        log.info("upload ajax");
    }

    @PostMapping("/uploadAjaxAction")
    public void uploadAjaxPost(MultipartFile[] uploadFile) {
        log.info("ajax 업로드 방식 ......");

        LocalDate now = LocalDate.now();
        log.info("오늘 날짜 : {}", now);


        String childPath = now.toString().replace("-", File.separator);
        File uploadPath = new File(saveLocation, childPath);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        for (MultipartFile file : uploadFile) {
            log.info("======");
            log.info("upload file name : {}", file.getOriginalFilename());
            log.info("upload file size : {}", file.getSize());
            log.info("upload file contentType : {}", file.getContentType());
            log.info("======");

            String fileName = file.getOriginalFilename();
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

            log.info("file name only : {}", fileName);

            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString() + "_" + fileName;

            try {
                File saveFile = new File(uploadPath, fileName);
                file.transferTo(saveFile);

                if (checkImageType(saveFile)) {
                    FileOutputStream outputStream = new FileOutputStream(new File(uploadPath, "s_" + fileName));
                    Thumbnailator.createThumbnail(file.getInputStream(),
                            outputStream, 100, 100);

                    outputStream.close();
                }

            } catch (IOException e) {
                log.error("파일 저장에서 오류 발생", e);
            }
        }
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (IOException e) {
            log.error("contentType 확인 중 오류! 파일을 찾을 수 없습니다.", e);
        }

        return false;
    }
}
