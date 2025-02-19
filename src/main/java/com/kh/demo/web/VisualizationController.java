package com.kh.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequestMapping("/visualization")
public class VisualizationController {
  private final String BASE64_URL = "http://localhost:8000/wordcloud";
  private final String IMG_FILE_URL = "http://localhost:8000/wordcloud2";

  @GetMapping("")
  public String wordcloud() {
    return "/visualization/wordCloud"; // Thymeleaf 템플릿 이름
  }

  // case1) base64 방식 : 간단한 이미지나 작은 크기의 이미지를 전송할 때 유용
  @GetMapping("/get-wordcloud")
  @ResponseBody
  public ResponseEntity<String> getWordCloud() {
    RestTemplate restTemplate = new RestTemplate();
    try {
      String response = restTemplate.getForObject(BASE64_URL, String.class);
      log.info("BASE64_URL={}", response);
      return ResponseEntity.ok(response);
    } catch (RestClientException e) {
      log.error("Error connecting to FastAPI server: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error connecting to FastAPI server: " + e.getMessage());
    }
  }

  // case2) 파일 경로 방식 : 대용량 이미지나 여러 이미지를 처리할 때 더 효율적
  @GetMapping("/get-wordcloud2")
  @ResponseBody
  public ResponseEntity<String> getWordCloud2() {
    RestTemplate restTemplate = new RestTemplate();
    try {
      String response = restTemplate.getForObject(IMG_FILE_URL, String.class);
      // JSON 파싱
      String imagePath = response.substring(response.indexOf(":") + 2, response.length() - 2); // 파일 경로 추출
      log.info("IMG_FILE_URL={},{}", response, imagePath);
      return ResponseEntity.ok(response);
    } catch (RestClientException e) {
      log.error("Error connecting to FastAPI server: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error connecting to FastAPI server: " + e.getMessage());
    }
  }
}