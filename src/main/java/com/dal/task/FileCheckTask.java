package com.dal.task;

import com.dal.domain.BoardAttachVO;
import com.dal.mapper.BoardAttachMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j@RequiredArgsConstructor
@Component
public class FileCheckTask {

    private final BoardAttachMapper attachMapper;

    private String getFolderYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);

        String str = sdf.format(cal.getTime());

        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void checkFiles() {
        log.warn("File Check Task run ..........................");
        log.warn(new Date());

        // file list in database
        List<BoardAttachVO> fileList = attachMapper.getOldFiles();

        // ready for check file in directory with database file list
        List<Path> fileListPaths = fileList.stream()
                .map(vo -> Paths.get("D:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
                .collect(Collectors.toList());

        // image file has thumnail file
        fileList.stream().filter(BoardAttachVO::isFileType)
                .map(vo -> Paths.get("D:\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
                .forEach(fileListPaths::add);

        log.warn("==============================================");

        fileListPaths.forEach(log::warn);

        // files in yesterday directory
        File targetDir = Paths.get("D:\\upload", getFolderYesterday()).toFile();

        File[] removeFiles = targetDir.listFiles(file -> !fileListPaths.contains(file.toPath()));

        log.warn("----------------------------------------------");
        for (File file : removeFiles) {
            log.warn(file.getAbsolutePath());
            file.delete();
        }
    }
}
