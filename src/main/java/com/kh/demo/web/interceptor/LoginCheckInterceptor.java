package com.kh.demo.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
@Component  // springboot구동시 객체가 자동으로 생성되어 빈 컨테이너에서 관리
public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //리다이렉트 Url
    String redirectUrl = null;

    log.info("handler={}", handler.getClass());
    //요청 URI    ex) GET http://localhost:9080/products?a=1&b=2 상품관리
    String requestURI = request.getRequestURI();    //   /products
//    log.info("requestURI={}",request.getRequestURI());   // /products
//    log.info("queryString="+request.getQueryString());   // a=1&b=2
//    log.info("queryString="+request.getRequestURL());   // http://localhost:9080/products
//    log.info("queryString="+request.getMethod());   // GET,POST

    //요청 url에 쿼리스트링이 없는경우
    if(request.getQueryString() == null){
      redirectUrl = requestURI;  // /products
    }else{
      //요청 url에 쿼리스트링이 있는 경우
      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8"); // a=1&b=2
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI).append("?").append(queryString).toString();// /products?a=1&b=2
    }

    //세션조회
    HttpSession session = request.getSession(false);

    //세션이 없거나 loginOkMember정보가 없으면 로그인 페이지로 리다이렉트
    if (session == null || session.getAttribute("loginMember") == null) {
//      log.info("미인증 요청");
      response.sendRedirect("/login?redirectUrl=" + redirectUrl);  // 302 GET http://localhost:9080/login
    }

    // true를 반환하면 다음 인터셉터 또는 핸들러로 진행하고,
    // false를 반환하면 요청 처리를 중단함
    return true;
  }
}
