package app.handong.feed.service;

import com.google.cloud.storage.Bucket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService {

    public String getSignedUrl(String filePath) {
        return getSignedUrl(filePath, 30);
    }

    public String getSignedUrl(String filePath, int durationMin) {
        try {
            Storage storage = StorageClient.getInstance().bucket().getStorage();
            String bucketName = StorageClient.getInstance().bucket().getName();

            BlobId blobId = BlobId.of(bucketName, filePath);
            Blob blob = storage.get(blobId);

            if (blob == null) {
                throw new RuntimeException("File not found: " + filePath);
            }

            return blob.signUrl(durationMin, TimeUnit.MINUTES).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Async
    public CompletableFuture<String> getSignedUrlAsync(String path) {
        String url = getSignedUrl(path);  // Assume this is the existing method to get signed URL
        return CompletableFuture.completedFuture(url);
    }

    public List<String> listAllFiles(String folderName) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        Bucket bucket = StorageClient.getInstance().bucket();

        List<String> fileList = new ArrayList<>();
//        for (Blob blob : bucket.list().iterateAll()) {
        for (Blob blob : bucket.list(Storage.BlobListOption.prefix(folderName + "/")).iterateAll()) {
            fileList.add(blob.getName());
        }

        return fileList;
    }

    @Async
    public CompletableFuture<List<String>> listAllFilesAsync(String folderName) {
        List<String> files = listAllFiles(folderName);
        return CompletableFuture.completedFuture(files);
    }
}
