package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.dao.MemberDAO;
import com.kh.demo.web.form.login.LoginForm;
import com.kh.demo.web.form.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberDAO memberDAO;

  //로그인화면
  @GetMapping("/login")     // http://localhost:9080/login
  public String loginForm(Model model){
    model.addAttribute("loginForm", new LoginForm());
    return "/login/loginForm";
  }
  //로그인처리
  @PostMapping("/login")  // http://localhost:9080/login?redirectUrl=/products
  public String login(
          LoginForm loginForm, BindingResult bindingResult,
          @RequestParam(name="redirectUrl",defaultValue = "/") String redirectUrl,  //   /products
          HttpServletRequest request){
    log.info("loginForm={}",loginForm);

    //1) 회원존재유무 체크
    if(!memberDAO.isExist(loginForm.getEmail())){
      bindingResult.rejectValue("email","invalidMember");
      return "/login/loginForm";
    }
    //2) 비밀번호 일치여부 체크
    Optional<Member> optionalMember = memberDAO.findByEmail(loginForm.getEmail());
    Member member = optionalMember.get();
    log.info("member={}",member);
    
    if(!loginForm.getPasswd().equals(member.getPasswd())){
      bindingResult.rejectValue("passwd","invalidMember");
      return "/login/loginForm";
    }
    //3) 로그인 세션 반영
    //세션이 있으면 존재하면 해당 세션을 가져오고 없으면 신규생성
    HttpSession session = request.getSession(true);

    //4) 세션에 회원정보 저장
    LoginMember loginMember = new LoginMember(
            member.getMemberId(),
            member.getEmail(),
            member.getNickname(),
            member.getGender());
    session.setAttribute("loginMember",loginMember);

    return "redirect:"+redirectUrl;   //로그인 전 요청 URL로 이동
  }
  //로그아웃처리
  @GetMapping("/logout")
  public String logout(HttpServletRequest request){
    //세션정보 가져오기
    HttpSession session = request.getSession(false);

    //세션제거
    if(session != null ){
      session.invalidate();
    }
    return "redirect:/";
  }
}
