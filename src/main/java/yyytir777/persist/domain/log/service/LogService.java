package yyytir777.persist.domain.log.service;

import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;

import java.util.List;

public interface LogService {

    String saveLog(LogCreateRequestDto logCreateRequestDto, String memberId);

    LogResponseDto readLog(String logId);

    List<LogResponseDto> readAllLogs();

    List<LogResponseDto> readAllLogsByMemberId(String memberId);

    LogResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, String logId, String memberId);

    void deleteLog(String logId, String memberId);

}
