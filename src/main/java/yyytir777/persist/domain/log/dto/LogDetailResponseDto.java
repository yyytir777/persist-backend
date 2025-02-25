package yyytir777.persist.domain.log.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.log.entity.Log;

import java.time.LocalDate;

@Getter
@Builder
public class LogDetailResponseDto {

    // log
    private Long id;
    private String title;
    private String thumbnail;
    private long viewCount;
    private LocalDate modifiedDate;
    private String content;

    // member
    private Long memberId;
    private String author;
    private String authorThumbnail;

    public static LogDetailResponseDto of(Log log) {
        return LogDetailResponseDto.builder()
                .id(log.getId())
                .title(log.getTitle())
                .thumbnail(log.getThumbnail())
                .viewCount(log.getViewCount())
                .modifiedDate(log.getModifiedTime().toLocalDate())
                .content(log.getContent())
                .memberId(log.getCategory().getMember().getId())
                .author(log.getCategory().getMember().getName())
                .authorThumbnail(log.getCategory().getMember().getThumbnail())
                .build();
    }
}
