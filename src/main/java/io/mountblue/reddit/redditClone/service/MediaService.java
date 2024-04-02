package io.mountblue.reddit.redditClone.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MediaService {

    String uploadMediaToBucket(MultipartFile file, String fileNamePrefix);

    Resource fetchMediaFromUri(String filepath);

    public static String replaceWhiteSpaces(String str){
        return str.replaceAll("\\s+", "_");
    }

}
