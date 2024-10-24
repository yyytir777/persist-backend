package yyytir777.persist.global.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.MemberException;
import yyytir777.persist.global.jwt.JwtUtil;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;

@Service
@RequiredArgsConstructor
public class ApplicationLoginService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public JwtInfoDto login(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        return jwtUtil.createToken(MemberInfoDto.of(member));
    }
}
