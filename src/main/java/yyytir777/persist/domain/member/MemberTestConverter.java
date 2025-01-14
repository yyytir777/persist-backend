package yyytir777.persist.domain.member;

import yyytir777.persist.domain.member.entity.Member;

public class MemberTestConverter {

    public static Member createMemberInTest(Long memberId) {
        return new Member(memberId);
    }
}
