package yyytir777.persist.domain.log;

import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.entity.Log;

public class LogConverter {

    public static Log CreateRequestToEntity(LogCreateRequestDto logCreateRequestDto, Category category) {
        return Log.builder()
                .category(category)
                .title(logCreateRequestDto.getTitle())
                .thumbnail(logCreateRequestDto.getThumbnail())
                .content(logCreateRequestDto.getContent())
                .preview(logCreateRequestDto.getContent().substring(0, Math.min(logCreateRequestDto.getContent().length(), 30)))
                .viewCount(0)
                .build();
    }

    public static Log UpdateRequestToEntity(LogUpdateRequestDto logUpdateRequestDto, Long logId, Category category, long viewCount) {
        return Log.builder()
                .id(logId)
                .title(logUpdateRequestDto.getTitle())
                .thumbnail(logUpdateRequestDto.getThumbnail())
                .content(logUpdateRequestDto.getContent())
                .preview(logUpdateRequestDto.getContent().substring(0, Math.min(logUpdateRequestDto.getContent().length(), 30)))
                .viewCount(viewCount)
                .category(category)
                .build();
    }
}
