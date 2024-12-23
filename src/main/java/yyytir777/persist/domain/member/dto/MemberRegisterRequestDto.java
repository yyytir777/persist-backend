package yyytir777.persist.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import yyytir777.persist.domain.common.Type;

@Getter
public class MemberRegisterRequestDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "로그이름은 필수 입력값입니다.")
    private String logName;

    private Type type;
}
