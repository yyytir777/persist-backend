package yyytir777.persist.domain.log.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.log.entity.Log;

@Getter
@Builder
public class LogResponseDto {

    private String title;
    private String content;
    private long viewCount;

    public static LogResponseDto of(Log log) {
        return LogResponseDto.builder()
                .title(log.getTitle())
                .content(log.getContent())
                .viewCount(log.getViewCount())
                .build();
    }
}
