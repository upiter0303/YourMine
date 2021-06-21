package com.bit.yourmain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;

@Service
public class FileService {

    private String getSaveFileName(String extName, String rootName) {
        String fileName = rootName+"_";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName.toLowerCase();

        return fileName;
    }

    public String fileSave(MultipartFile profile, String filePath) {
        try {
            String savePath = "C:\\Users\\User\\Documents\\"+filePath;
            String origin = profile.getOriginalFilename();
            String nameCut = origin.substring(origin.lastIndexOf("."));
            String saveFileName = getSaveFileName(nameCut, filePath);

            if(!profile.isEmpty())
            {
                File file = new File(savePath, saveFileName);
                profile.transferTo(file);
                return saveFileName;
            }
        }catch(Exception e)
        {
            e.getCause();
        }
        return null;
    }
}
