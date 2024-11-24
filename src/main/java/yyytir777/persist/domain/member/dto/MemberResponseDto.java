package yyytir777.persist.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.member.entity.Member;

@Getter
@Builder
public class MemberResponseDto {

    private String email;
    private String name;
    private String logName;
    private String thumbnail;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .logName(member.getMemberLogName())
                .thumbnail(member.getThumbnail())
                .build();
    }
}
