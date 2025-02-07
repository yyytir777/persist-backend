package yyytir777.persist.domain.log.service;

import org.springframework.data.domain.Page;
import yyytir777.persist.domain.log.dto.LogDetailResponseDto;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogThumbnailResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;

import java.util.List;

public interface LogService {

    Long saveLog(LogCreateRequestDto logCreateRequest);

    LogDetailResponseDto readLog(Long logId, boolean hasViewed);

    Page<LogThumbnailResponseDto> readAllLogs(int page, int size);

    List<LogThumbnailResponseDto> readAllLogsByMemberId(Long memberId);

    LogDetailResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, Long logId);

    void deleteLog(Long logId);

}
