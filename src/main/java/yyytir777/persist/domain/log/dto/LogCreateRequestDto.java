package yyytir777.persist.domain.log.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogCreateRequestDto {

    private String title;
    private String thumbnail;
    private String content;
}
