package com.kh.demo.web;

import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.api.ApiResponseCode;
import com.kh.demo.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
// 글로벌 예외 핸들러에서의 사용
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 비즈니스 예외 처리
  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleBusinessException(BusinessException e) {
    log.info("Business exception occurred: {}", e.getMessage());
    return ApiResponse.of(e.getResponseCode(),null);
  }

  // Validation 예외 - details 포함
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException e) {
    Map<String, String> details = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage
            ));

    return ApiResponse.withDetails(
            ApiResponseCode.VALIDATION_ERROR,
            details,
            null
    );
  }

  // 일반 예외 - details 없음
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResponse<Object> handleException(Exception e) {
    log.error("Unexpected error occurred", e);
    return ApiResponse.of(ApiResponseCode.INTERNAL_SERVER_ERROR, null);
  }
}
