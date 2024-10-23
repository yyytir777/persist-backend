package yyytir777.persist.domain.log.service;

import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogSaveRequestDto;

import java.util.List;

public interface LogService {

    void save(LogSaveRequestDto logSaveRequestDto);

    LogResponseDto getLog(String logId);

    List<LogResponseDto> getAllLogs();
}
