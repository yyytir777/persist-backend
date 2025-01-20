package yyytir777.persist.domain.member.service;


import yyytir777.persist.domain.member.dto.MemberRegisterRequestDto;
import yyytir777.persist.domain.member.dto.MemberResponseDto;
import yyytir777.persist.domain.member.dto.MemberUpdateRequestDto;
import yyytir777.persist.domain.member.entity.Member;


public interface MemberService {

    void register(MemberRegisterRequestDto memberRegisterRequestDto);

    MemberResponseDto readMember(Long memberId);

    MemberResponseDto updateMember(MemberUpdateRequestDto memberUpdateRequestDto, Long memberId);

    void deleteMember(Long memberId);

    Member findByEmail(String email);

    String getReadme(Long memberId);
}
