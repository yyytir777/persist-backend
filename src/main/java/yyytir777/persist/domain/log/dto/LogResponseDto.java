package yyytir777.persist.domain.log.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@Builder
public class LogResponseDto {

    // 로그
    private String id;
    private String title;
    private String thumbnail;
    private String preview;
    private long viewCount;
    private LocalDate createdDate;
    private String content;

    // 멤버 (author)
    private String memberId;
    private String name;
    private String authorThumbnail;


    public static LogResponseDto of(Log log, Member member) {
        return LogResponseDto.builder()
                .id(log.getId())
                .title(log.getTitle())
                .thumbnail(log.getThumbnail())
                .preview(log.getPreview())
                .viewCount(log.getViewCount())
                .createdDate(log.getCreatedTime().toLocalDate())
                .memberId(member.getId())
                .name(member.getName())
                .authorThumbnail(member.getThumbnail())
                .content(log.getContent())
                .build();
    }
}
