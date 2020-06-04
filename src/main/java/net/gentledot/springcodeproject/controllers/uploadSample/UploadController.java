package net.gentledot.springcodeproject.controllers.uploadSample;

import net.coobird.thumbnailator.Thumbnailator;
import net.gentledot.springcodeproject.model.upload.AttachFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UploadController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String saveLocation = "C:\\upload\\";

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
    public ResponseEntity<List<AttachFile>> uploadAjaxPost(MultipartFile[] uploadFile) {
        log.info("ajax 업로드 방식 ......");

        ArrayList<AttachFile> fileList = new ArrayList<>();

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
            String uniqueFileName = uuid.toString() + "_" + fileName;

            try {
                File saveFile = new File(uploadPath, uniqueFileName);
                file.transferTo(saveFile);

                AttachFile attachFile = new AttachFile.Builder()
                        .fileName(fileName)
                        .uuid(uuid.toString())
                        .uploadPath(childPath)
                        .build();

                if (checkImageType(saveFile)) {
                    attachFile.setImage(true);

                    FileOutputStream outputStream = new FileOutputStream(new File(uploadPath, "s_" + uniqueFileName));
                    Thumbnailator.createThumbnail(file.getInputStream(),
                            outputStream, 100, 100);

                    outputStream.close();
                }

                fileList.add(attachFile);

            } catch (IOException e) {
                log.error("파일 저장에서 오류 발생", e);
            }
        }

        return new ResponseEntity<>(fileList, HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {
        log.info("fileName : {}", fileName);

        File file = new File(saveLocation + fileName);

        log.info("file 확인 : {}", file);

        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            log.error("업로드 파일 가져오기 실패!", e);
        }

        return result;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
        log.info("다운로드 파일 : {}", fileName);
        log.info("다운로드 파일_UTF-8 decoding : {}", URLDecoder.decode(URLDecoder.decode(fileName, StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        log.info("agent 확인 : {}", userAgent);

        FileSystemResource resource = new FileSystemResource(saveLocation + fileName);
        log.info("resource 확인 : {}", resource);

        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        String resourceName = resource.getFilename();
        String resouceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
        HttpHeaders headers = new HttpHeaders();

        String downloadName = null;

        if (userAgent.contains("Trident")) {
            log.info("Internet Explorer 11 요청.");
            String encode = URLEncoder.encode(resouceOriginalName, StandardCharsets.UTF_8);
            log.info("resourceName_UTF-8 Encode : {}", encode);
            downloadName = encode.replaceAll("\\+", " ");
        } else if (userAgent.contains("Edge")) {
            log.info("Edge Legacy 요청.");
            downloadName = URLEncoder.encode(resouceOriginalName, StandardCharsets.UTF_8);
        } else {
            downloadName = new String(resouceOriginalName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        log.info("downloadName : {}", downloadName);

        headers.add("Content-Disposition", "attachment; filename=" + downloadName);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
