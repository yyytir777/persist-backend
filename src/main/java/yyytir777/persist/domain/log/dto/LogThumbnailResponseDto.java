package yyytir777.persist.domain.log.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@Builder
public class LogThumbnailResponseDto {

    // 로그
    private String id;
    private String title;
    private String thumbnail;
    private String preview;
    private long viewCount;
    private LocalDate modifiedDate;

    // 멤버 (author)
    private String memberId;
    private String name;
    private String authorThumbnail;


    public static LogThumbnailResponseDto of(Log log) {
        return LogThumbnailResponseDto.builder()
                .id(log.getId())
                .title(log.getTitle())
                .thumbnail(log.getThumbnail())
                .preview(log.getPreview())
                .viewCount(log.getViewCount())
                .modifiedDate(log.getCreatedTime().toLocalDate())
                .memberId(log.getMember().getId())
                .name(log.getMember().getName())
                .authorThumbnail(log.getMember().getThumbnail())
                .build();
    }
}
