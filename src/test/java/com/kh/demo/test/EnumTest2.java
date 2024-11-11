package com.kh.demo.test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class EnumTest2 {
//  @Test
  void t1() {
    Arrays.stream(ApiResponseCode.values()).forEach(ele->log.info("{},{}",ele.getCode(),ele.getMessage()));
  }

  @Getter
  @RequiredArgsConstructor
  static enum ApiResponseCode {
    // 성공 응답
    SUCCESS("0", "Success"),

    // 공통 예외
    VALIDATION_ERROR("E001", "Validation error occurred"),
    BUSINESS_ERROR("E002", "Business error occurred"),
    ENTITY_NOT_FOUND("E003", "Entity not found"),

    // 사용자 관련 예외
    USER_NOT_FOUND("U001", "User not found"),
    USER_ALREADY_EXISTS("U002", "User already exists"),
    INVALID_PASSWORD("U003", "Invalid password"),

    // 주문 관련 예외
    ORDER_NOT_FOUND("O001", "Order not found"),
    INSUFFICIENT_STOCK("O002", "Insufficient stock"),

    // 시스템 예외
    INTERNAL_SERVER_ERROR("9999", "Internal server error");

    private final String code;
    private final String message;
  }

}
