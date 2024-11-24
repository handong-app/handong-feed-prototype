package app.handong.feed.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 파일 업로드시 일어나는 예외에 사용되는 예외처리
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
@NoArgsConstructor
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}