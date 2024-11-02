package app.handong.feed.service;

import app.handong.feed.dto.TblostItemFileDto;
import app.handong.feed.exception.FileUploadException;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class FirebaseService {
    private final Bucket bucket;
    private final TblostItemFileService tblostItemFileService;

    public FirebaseService(FirebaseApp firebaseApp, TblostItemFileService tblostItemFileService) {
        this.bucket = StorageClient.getInstance(firebaseApp).bucket();
        this.tblostItemFileService = tblostItemFileService;
    }

    /**
     * 파일 URL을 생성하는 헬퍼 메서드
     *
     * @param fileName 파일 경로 및 이름
     * @return Firebase Storage에 접근 가능한 파일 URL
     */
    public String generateFileUrl(String fileName) {
        return "https://firebasestorage.googleapis.com/v0/b/"
                + bucket.getName() + "/o/"
                + fileName.replaceAll("/", "%2F") + "?alt=media";
    }

    /**
     * 파일 해시 생성 메서드 (16진수 인코딩)
     *
     * @param file 파일 경로 및 이름
     * @return 파일 해시값
     */
    private String hashFileToHex(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputStream.readAllBytes());
            // 바이트 배열을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("파일 해싱 실패: " + file.getOriginalFilename(), e);
        }
    }

    /**
     * 다중 파일 업로드
     *
     * @param files 업로드할 MultipartFile 목록
     * @param folder 저장할 폴더명 (예: "LostItem")
     * @param itemId 파일을 구분하는 ID (예: 분실물 ID)
     * @return 업로드된 파일의 공개 URL 목록
     */
    public List<String> uploadFiles(List<MultipartFile> files, String folder, String itemId) {
        List<String> fileUrls = new ArrayList<>();
        int index = 1;

        for (MultipartFile file : files) {
            try {
                // 파일의 해시값을 생성하여 파일 이름에 추가
                String fileHash = hashFileToHex(file);
                String fileName = folder + "/" + itemId + "_" + fileHash;
                Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

                long fileSize = blob.getSize();
                String fileType = blob.getContentType();

                log.info("📍 Uploaded file name: {}", file.getOriginalFilename());
                log.info("📍 Uploaded file size: {}", fileSize);
                log.info("📍 Uploaded file type: {}", fileType);

                String fileUrl = generateFileUrl(fileName);
                fileUrls.add(fileUrl);

                tblostItemFileService.createLostItemFile(TblostItemFileDto.CreateServDto.builder()
                        .tblostId(itemId)
                        .fileName(fileName)
                        .fileType(fileType)
                        .fileOrder(index)
                        .build()
                );
                index++;
            } catch (IOException e) {
                throw new FileUploadException("File upload failed: " + file.getOriginalFilename(), e);
            }
        }

        return fileUrls;
    }
    /**
     * 파일 경로를 이용하여 signed URL을 생성
     *
     * @param filePath 파일의 Firebase Storage 경로
     * @return signed URL
     */
    public String getSignedUrl(String filePath) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        String bucketName = StorageClient.getInstance().bucket().getName();

        BlobId blobId = BlobId.of(bucketName, filePath);
        Blob blob = storage.get(blobId);

        if (blob == null) {
            throw new RuntimeException("File not found: " + filePath);
        }

        return blob.signUrl(30, TimeUnit.MINUTES).toString();
    }

    /**
     * 비동기적으로 signed URL을 생성
     *
     * @param path 파일의 Firebase Storage 경로
     * @return 비동기 signed URL
     */
    @Async
    public CompletableFuture<String> getSignedUrlAsync(String path) {
        String url = getSignedUrl(path);  // Assume this is the existing method to get signed URL
        return CompletableFuture.completedFuture(url);
    }

    /**
     * 폴더 내 모든 파일의 경로를 반환
     *
     * @param folderName 폴더명
     * @return 파일 경로 목록
     */
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

    /**
     * 비동기적으로 폴더 내 모든 파일의 경로를 반환
     *
     * @param folderName 폴더명
     * @return 비동기 파일 경로 목록
     */
    @Async
    public CompletableFuture<List<String>> listAllFilesAsync(String folderName) {
        List<String> files = listAllFiles(folderName);
        return CompletableFuture.completedFuture(files);
    }
}
