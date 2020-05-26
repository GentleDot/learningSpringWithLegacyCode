package net.gentledot.springcodeproject.controllers.uploadSample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

        for (MultipartFile file : uploadFile) {
            log.info("======");
            log.info("upload file name : {}", file.getOriginalFilename());
            log.info("upload file size : {}", file.getSize());
            log.info("upload file contentType : {}", file.getContentType());
            log.info("======");

            String fileName = file.getOriginalFilename();
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

            log.info("file name only : {}", fileName);
            File saveFile = new File(saveLocation, fileName);

            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                log.error("파일 저장에서 오류 발생", e);
            }
        }
    }
}
