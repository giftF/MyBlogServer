package org.gift.Utiliy.aspect;


import org.springframework.web.multipart.MultipartFile;

public interface UploadFileServerInterface {
    public String uploadeFile(MultipartFile multipartFile);
}
