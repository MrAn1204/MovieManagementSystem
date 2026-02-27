package com.mms.mms_api.business.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.exception.InvalidInputException;

@Service
public class GscService {
    private static final String BUCKET_NAME = "bucket-mms-488508";

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
}
