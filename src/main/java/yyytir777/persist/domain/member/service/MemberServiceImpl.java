package yyytir777.persist.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.domain.member.dto.MemberRegisterRequestDto;
import yyytir777.persist.domain.member.dto.MemberResponseDto;
import yyytir777.persist.domain.member.dto.MemberUpdateRequestDto;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.MemberException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void register(MemberRegisterRequestDto memberRegisterRequestDto) {

        Optional<Member> findMember = memberRepository.findByEmail(memberRegisterRequestDto.getEmail());

        if(findMember.isPresent()) {
            throw new MemberException(ErrorCode.MEMBER_EXIST);
        } else {
            Member member = Member.builder()
                    .email(memberRegisterRequestDto.getEmail())
                    .name(memberRegisterRequestDto.getName())
                    .memberLogName(memberRegisterRequestDto.getLogName())
                    .type(memberRegisterRequestDto.getType())
                    .role(Role.USER)
                    .build();

            memberRepository.save(member);
        }
    }

    @Override
    public MemberResponseDto readMember(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        return MemberResponseDto.of(member);
    }

    @Override
    public MemberResponseDto updateMember(MemberUpdateRequestDto memberUpdateRequestDto, String memberId, String currentMemberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        if(!memberId.equals(currentMemberId)) throw new MemberException(ErrorCode.NOT_MY_MEMBER);

        member = Member.builder()
                .id(memberId)
                .email(member.getEmail())
                .name(memberUpdateRequestDto.getName())
                .memberLogName(memberUpdateRequestDto.getLogName())
                .thumbnail(memberUpdateRequestDto.getThumbnail())
                .role(member.getRole())
                .type(member.getType())
                .build();

        memberRepository.save(member);
        return MemberResponseDto.of(member);
    }

    @Override
    public void deleteMember(String memberId, String currentMemberId) {
        if(!memberId.equals(currentMemberId)) throw new MemberException(ErrorCode.NOT_MY_MEMBER);

        memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        memberRepository.deleteById(memberId);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));
    }

    @Override
    public String getReadme(String memberId) {
        return memberRepository.findReadmeById(memberId);
    }

}
