package org.gift.Utiliy.aspect;

import org.apache.commons.io.IOUtils;
import org.gift.Utiliy.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileServer implements UploadFileServerInterface {

    @Value("${filepath}")
    private String path;

    @Override
    public String uploadeFile(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID().toString().replace("-", "");
        File targetFile = new File(path + File.separator + fileName);

        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);
            LogUtil.LOGGING.info("----->>>>>文件上传成功<<<<<-----");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                fileOutputStream.close();
                return fileName;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

















