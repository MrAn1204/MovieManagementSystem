package com.mms.mms_api.business.service;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.exception.InvalidInputException;

/**
 * Handles file operations against Google Cloud Storage.
 */
@Service
public class GscService {
    private static final String BUCKET_NAME = "bucket-mms-488508";

    /**
     * Uploads a file to the configured storage bucket.
     *
     * @param file multipart file to upload
     * @param folderPath target logical folder path
     * @return persisted bucket/object path or null when file is empty
     */
    public String upload(MultipartFile file, StoragePath folderPath) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        Storage storage = StorageOptions.getDefaultInstance().getService();

        String filePath = folderPath.getPath() + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        try {
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            throw new InvalidInputException("file.invalid");
        }

        return String.format("%s/%s", BUCKET_NAME, filePath);
    }

    /**
     * Deletes an object from storage by bucket/object path.
     *
     * @param filePath full bucket/object path
     * @return true when the object is deleted
     */
    public boolean delete(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        Storage storage = StorageOptions.getDefaultInstance().getService();

        String[] pathParts = filePath.split("/", 2);
        String bucketName = pathParts[0];
        String objectName = pathParts[1];

        BlobId blobId = BlobId.of(bucketName, objectName);

        return storage.delete(blobId);
    }

    /**
     * Returns a public CDN-style URL for a stored object.
     *
     * @param filePath full bucket/object path
     * @return public URL or null when path is empty
     */
    public String getPublicUrl(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        return String.format("https://storage.googleapis.com/%s", filePath);
    }

    /**
     * Returns a signed temporary URL for a stored object.
     *
     * @param filePath full bucket/object path
     * @return signed URL or null when path is empty
     */
    public String getUrl(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        Storage storage = StorageOptions.getDefaultInstance().getService();

        String[] pathParts = filePath.split("/", 2);
        String bucketName = pathParts[0];
        String objectName = pathParts[1];

        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();

        URL url = storage.signUrl(blobInfo, 5, TimeUnit.MINUTES);

        return url.toString();
    }
}
