package yyytir777.persist.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.entity.Member;

@Getter
@Builder
public class MemberInfoDto {
    private String memberId;
    private String email;
    private Role role;

    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }

    public static MemberInfoDto of(String id, String email, Role role) {
        return MemberInfoDto.builder()
                .memberId(id)
                .email(email)
                .role(role)
                .build();
    }

}
