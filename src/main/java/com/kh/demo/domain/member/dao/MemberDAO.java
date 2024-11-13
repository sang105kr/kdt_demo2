package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  // 가입
  Member insertMember(Member member);

  // 회원 존재 유무
  boolean isExist(String email);

  // 회원 조회
  Optional<Member> findByMemberId(Long memberId);
  Optional<Member> findByEmail(String email);
}
