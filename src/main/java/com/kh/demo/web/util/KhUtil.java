package com.kh.demo.web.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KhUtil {
  /**
   * Controller의 BindingResult를 ApiResponse에서 응답메세지 바디에 사용될 Map로 변환
   * @param bindingResult 요청데이터 검증오류
   * @return map 
   */
  public static Map<String, String> getValidChkMap(BindingResult bindingResult) {
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    Map<String,String> map = new HashMap<>();
    for (FieldError fieldError : fieldErrors) {
      map.put(fieldError.getField(),fieldError.getDefaultMessage());
    }
    return map;
  }
}
