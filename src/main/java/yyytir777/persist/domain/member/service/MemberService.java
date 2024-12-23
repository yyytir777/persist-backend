package yyytir777.persist.domain.member.service;


import yyytir777.persist.domain.member.dto.MemberRegisterRequestDto;
import yyytir777.persist.domain.member.dto.MemberResponseDto;
import yyytir777.persist.domain.member.dto.MemberUpdateRequestDto;
import yyytir777.persist.domain.member.entity.Member;


public interface MemberService {

    void register(MemberRegisterRequestDto memberRegisterRequestDto);

    MemberResponseDto readMember(String memberId);

    MemberResponseDto updateMember(MemberUpdateRequestDto memberUpdateRequestDto, String memberId, String currentMemberId);

    void deleteMember(String memberId, String currentMemberId);

    Member findByEmail(String email);
}
