package app.handong.feed.service;

import app.handong.feed.dto.TblostItemFileDto;
import app.handong.feed.exception.FileUploadException;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FirebaseStorageService {

    private final Bucket bucket;
    private final TblostItemFileService tblostItemFileService;

    public FirebaseStorageService(FirebaseApp firebaseApp, TblostItemFileService tblostItemFileService) {
        this.bucket = StorageClient.getInstance(firebaseApp).bucket();
        this.tblostItemFileService = tblostItemFileService;
    }

    /**
     * 파일 URL을 생성하는 헬퍼 메서드
     *
     * @param fileName 파일 경로 및 이름
     * @return Firebase Storage에 접근 가능한 파일 URL
     */
    private String generateFileUrl(String fileName) {
        return "https://firebasestorage.googleapis.com/v0/b/"
                + bucket.getName() + "/o/"
                + fileName.replaceAll("/", "%2F") + "?alt=media";
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
                String fileName = folder + "/" + itemId + "_" + index;
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
                        .build()
                );

                index++;
            } catch (IOException e) {
                throw new FileUploadException("File upload failed: " + file.getOriginalFilename(), e);
            }
        }

        return fileUrls;
    }


}