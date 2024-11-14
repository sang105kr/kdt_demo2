package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.form.member.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
  private final MemberSVC memberSVC;

  //가입화면
  @GetMapping("/join")
  public String joinForm(Model model){

    model.addAttribute("joinForm",new JoinForm());
    return "/member/joinForm";
  }


  //가입처리
  @PostMapping("/join")
  public String join(@Valid JoinForm joinForm, BindingResult bindingResult, Model model){

    log.info("joinForm={}", joinForm);

    if (bindingResult.hasErrors()) {
      model.addAttribute("joinForm",joinForm);
      return "/member/joinForm";
    }


    Member member = new Member();
    BeanUtils.copyProperties(joinForm,member);

    // List의 취미요소를 콤마를 구분자로하여 문자열로 변환
    member.setHobby(String.join(",",joinForm.getHobby()));
    Member joinedMember = memberSVC.join(member);

    return "redirect:/login";
  }

}
