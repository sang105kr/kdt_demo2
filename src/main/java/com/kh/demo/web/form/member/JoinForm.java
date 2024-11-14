package com.kh.demo.web.form.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class JoinForm {
  @NotBlank(message = "이메일은 필수!")
  private String email;           //  EMAIL	VARCHAR2(50 BYTE)
  private String passwd;          //  PASSWD	VARCHAR2(12 BYTE)
  private String tel;             //  TEL	VARCHAR2(13 BYTE)
  private String nickname;        //  NICKNAME	VARCHAR2(30 BYTE)
  private String gender;          //  GENDER	VARCHAR2(6 BYTE)
  private List<String> hobby;     //  HOBBY	VARCHAR2(300 BYTE)
  private String region;          //  REGION	VARCHAR2(11 BYTE)
}
