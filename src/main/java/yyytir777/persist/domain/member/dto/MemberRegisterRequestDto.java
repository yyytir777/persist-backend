package yyytir777.persist.domain.member.dto;

import lombok.Getter;
import yyytir777.persist.domain.common.Type;

@Getter
public class MemberRegisterRequestDto {

    private String email;
    private String name;
    private String logName;
    private Type type;
}
