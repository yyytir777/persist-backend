package yyytir777.persist.domain.member;

import yyytir777.persist.domain.member.entity.Member;

public class MemberTestConvertor {

    public static Member createMemberInTest(Long memberId) {
        return new Member(memberId);
    }
}
