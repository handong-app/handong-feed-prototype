package app.handong.feed.exception;

import app.handong.feed.dto.DefaultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice(assignableTypes = {TbuserRestController.class, TbgroupRestController.class})
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<DefaultDto.IdResDto> handleNoAuthorizationException(NoAuthorizationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(DefaultDto.IdResDto.builder().id(ex.getMessage()).build());
    }


}
