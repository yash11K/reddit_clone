package io.mountblue.reddit.redditClone.service;

import com.google.cloud.spring.storage.GoogleStorageResource;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class MediaManager implements MediaService{
    private final Storage storage;
    @Autowired
    public MediaManager(Storage storage) {
        this.storage = storage;
    }

    @Value("${gcs-resource-test-bucket}")
    private String bucketName;

    @Override
    public String uploadMediaToBucket(MultipartFile file, String fileNamePrefix){
        try {

            Storage storage = StorageOptions.getDefaultInstance().getService();
            Bucket bucket = storage.get(bucketName);
            String filePath = UUID.randomUUID() + "_" + fileNamePrefix + "_" + MediaService.replaceWhiteSpaces(Objects.requireNonNull(file.getOriginalFilename()));
            Blob blob = bucket.create(filePath, file.getBytes(), file.getContentType());
            return filePath;
        } catch (IOException e) {
            return "Failed to upload file";
        }
    }

    @Override
    public Resource fetchMediaFromUri(String filepath){
        String googleAppCredentials = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        Resource fileResource = fetchResource(filepath); //"trial/a.png", a.png
        MediaType mediaType = MediaType.IMAGE_PNG;
        return fileResource;
    }

    private GoogleStorageResource fetchResource(String filename) {
        return new GoogleStorageResource(
                storage, String.format("gs://%s/%s", this.bucketName, filename));
    }
}
