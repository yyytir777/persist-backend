package yyytir777.persist.domain.log.service;

import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;

import java.util.List;

public interface LogService {

    void save(LogCreateRequestDto logCreateRequestDto);

    LogResponseDto readLog(String logId);

    List<LogResponseDto> readAllLogs();

    void update(LogUpdateRequestDto logUpdateRequestDto, String logId);

    void delete(String logId);
}
