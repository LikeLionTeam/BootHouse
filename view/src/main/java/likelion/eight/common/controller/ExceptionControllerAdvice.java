package likelion.eight.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.common.domain.exception.FileStorageException;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;



@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class ExceptionControllerAdvice {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                                HttpServletRequest request,
//                                                                HttpServletResponse response) throws IOException {
//        log.error("리소스를 찾을 수 없음: {} - 요청 URL: {}", exception.getMessage(), request.getRequestURL());
//        response.sendRedirect("/error/404");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//
//    @ExceptionHandler(FileStorageException.class)
//    public ResponseEntity<Void> handleFileStorageException(FileStorageException exception,
//                                                            HttpServletRequest request,
//                                                            HttpServletResponse response) throws IOException {
//        log.error("파일 저장 오류: {} - 요청 URL: {}", exception.getMessage(), request.getRequestURL());
//        response.sendRedirect("/error/500");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

    @ExceptionHandler(CertificationFailedException.class)
    public ResponseEntity<Void> handleCertificationFailedException(CertificationFailedException exception,
                                                                    HttpServletRequest request,
                                                                    HttpServletResponse response) throws IOException {
        log.warn("인증 실패: {} - 요청 URL: {}", exception.getMessage(), request.getRequestURL());
        response.sendRedirect("/login");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}