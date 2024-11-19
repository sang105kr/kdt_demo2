package com.kh.demo;

import com.kh.demo.web.interceptor.ExecutionTimeInterceptor;
import com.kh.demo.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

  private final LoginCheckInterceptor loginCheckInterceptor;
  private final ExecutionTimeInterceptor executionTimeInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //인증체크
    // 와일드 카드 경로패턴의 의미
    // * 는 0개 이상의 문자와 일치,  /api/*  => /api/products  (O), /api/members  (O)    /api/products/123 (x)
    // ** 는 0개 이상의 경로와 일치  /api/** => /api/products  (O), /api/members  (O)    /api/products/123 (O)
    // ? 는 1개의 문자와 일치       /api/products/? => /api/products/1  (O), /api/products/12 (X)
    registry.addInterceptor(loginCheckInterceptor)
            .order(2) //인터셉터 실행 순서 지정
            .addPathPatterns("/**")   // 루트부터 하위경로 모두 인터셉터에 포함
            .excludePathPatterns(              // 제외패턴
                    "/",              // 초기화면
                    "/login",
                    "/logout",
                    "/members/join",
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/api/**",
                    "/test/**",
                    "/error/**",
                    "/webjars/**"
            );
    //handler 실행시간 측정
    registry.addInterceptor(executionTimeInterceptor)
            .order(1)
            .addPathPatterns("/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")                  //요청 URL
            .allowedOrigins("http://localhost:5501")  //요청 Client
            .allowedMethods("*")                    //모든 Method
            .maxAge(3000);                            //캐쉬시간
  }

}
