package io.mountblue.reddit.redditClone.restcontroller;

import io.mountblue.reddit.redditClone.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaTestController {
    //only for dev. to test img output by directly giving blob.name like (a9ee640a-fdad-4f8c-ad6d-b945b015f4e1_opavatarimage.png)
    private MediaService mediaService;

//    @GetMapping("/fetch/{filePath}")
//    public ResponseEntity<Resource> fetchMediaFromUri(@PathVariable String filePath){
//        return return ResponseEntity.ok()
//                .contentType(mediaType)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
//                .body(fileResource);
//    }

    @PostMapping("/{fileFrom}/upload")
    public String uploadMedia(@PathVariable String fileFrom, @RequestBody MultipartFile file){
        return mediaService.uploadMediaToBucket(file, fileFrom);
    }
}
