package yyytir777.persist.domain.log.service;

import yyytir777.persist.domain.log.dto.LogDetailResponseDto;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogThumbnailResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;

import java.util.List;

public interface LogService {

    String saveLog(LogCreateRequestDto logCreateRequestDto, String memberId);

    LogDetailResponseDto readLog(String logId, boolean hasViewed);

    List<LogThumbnailResponseDto> readAllLogs();

    List<LogThumbnailResponseDto> readAllLogsByMemberId(String memberId);

    LogThumbnailResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, String logId, String memberId);

    void deleteLog(String logId, String memberId);

}
