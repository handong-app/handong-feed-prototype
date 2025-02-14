package app.handong.feed.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    /**
     * 파일 해시 생성 메서드 (16진수 인코딩)
     *
     * @param file 파일 경로 및 이름
     * @return 파일 해시값
     */
    public static String hashFileToHex(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputStream.readAllBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("파일 해싱 실패: " + file.getOriginalFilename(), e);
        }
    }
}
