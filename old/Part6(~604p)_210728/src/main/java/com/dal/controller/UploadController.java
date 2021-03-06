package com.dal.controller;

import com.dal.domain.AttachFileDTO;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j
public class UploadController {

    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("upload form");
    }

    @PostMapping("/uploadFormAction")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

        String uploadFolder = "D:\\upload";

        for (MultipartFile multipartFile : uploadFile) {
            log.info("-----------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload File Size : " + multipartFile.getSize());

            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

            try {
                multipartFile.transferTo(saveFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
        log.info("upload ajax");
    }

//    @PostMapping("/uploadAjaxAction")
//    public void uploadAjaxPost(MultipartFile[] uploadFile) {
//        log.info("update ajax post...............");
//
//        String uploadFolder = "D:\\upload";
//
//        // make folder
//        File uploadPath = new File(uploadFolder, getFolder());
//        log.info("upload path : " + uploadPath);
//
//        if (!uploadPath.exists()) {
//            uploadPath.mkdirs();
//        }
//        // make yyyy/MM/dd folder
//
//        for (MultipartFile multipartFile : uploadFile) {
//            log.info("-----------------------------------");
//            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
//            log.info("Upload File Size : " + multipartFile.getSize());
//
//            String uploadFileName = multipartFile.getOriginalFilename();
//
//            // IE has file path
//            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
//            log.info("only file name : " + uploadFileName);
//
//            UUID uuid = UUID.randomUUID();
//
//            uploadFileName = uuid.toString() + "_" + uploadFileName;
//
//            File saveFile = new File(uploadPath, uploadFileName);
//
//            try {
//                multipartFile.transferTo(saveFile);
//
//                // check image type file
//                if (checkImageType(saveFile)) {
//                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
//
//                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
//
//                    thumbnail.close();
//                }
//
//            } catch (IOException e) {
//                log.error(e.getMessage());
//            }
//        }
//    }

    @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
        log.info("update ajax post...............");

        List<AttachFileDTO> list = new ArrayList<>();

        String uploadFolder = "D:\\upload";
        String uploadFolderPath = getFolder();

        // make folder
        File uploadPath = new File(uploadFolder, uploadFolderPath);
        log.info("upload path : " + uploadPath);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        // make yyyy/MM/dd folder

        for (MultipartFile multipartFile : uploadFile) {
            AttachFileDTO attachDTO = new AttachFileDTO();

            log.info("-----------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload File Size : " + multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();

            // IE has file path
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            log.info("only file name : " + uploadFileName);

            attachDTO.setFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid.toString() + "_" + uploadFileName;

            File saveFile = new File(uploadPath, uploadFileName);

            try {
                multipartFile.transferTo(saveFile);

                attachDTO.setUuid(uuid.toString());
                attachDTO.setUploadPath(uploadFolderPath);

                // check image type file
                if (checkImageType(saveFile)) {
                    attachDTO.setImage(true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));

                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

                    thumbnail.close();
                }

                // add to List
                list.add(attachDTO);

            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {
        log.info("fileName : " + fileName);

        File file = new File("D:\\upload\\" + fileName);

        log.info("file : " + file);

        ResponseEntity<byte[]> result = null;

        HttpHeaders header = new HttpHeaders();

        try {
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
        log.info("download file : " + fileName);

        Resource resource = new FileSystemResource("D:\\upload\\" + fileName);

        if (!resource.exists()) {
            log.info("not found file : " + fileName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("resource : " + resource);

        String resourceName = resource.getFilename();

        // remove UUID
        String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();

        String downloadName = null;

        if (userAgent.contains("Trident")) {
            log.info("IE browser");

            downloadName = URLEncoder.encode(resourceOriginalName, StandardCharsets.UTF_8).replaceAll("\\+", " ");
        } else if (userAgent.contains("Edge")) {
            log.info("Edge browser");

            downloadName = URLEncoder.encode(resourceOriginalName, StandardCharsets.UTF_8);
        } else {
            log.info("Chrome browser");

            downloadName = new String(resourceOriginalName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        log.info("downloadName : " + downloadName);

        headers.add("Content-Disposition", "attachment; filename=" + downloadName);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String type) {
        log.info("deleteFile : " + fileName);

        File file;
        file = new File("D:\\upload\\" + URLDecoder.decode(fileName, StandardCharsets.UTF_8));
        file.delete();

        if (type.equals("image")) {
            String largeFileName = file.getAbsolutePath().replace("s_", "");
            log.info("largeFileName : " + largeFileName);

            file = new File(largeFileName);
            file.delete();
        }

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String str = sdf.format(date);

        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {

        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
