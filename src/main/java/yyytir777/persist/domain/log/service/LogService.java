package yyytir777.persist.domain.log.service;

import org.springframework.stereotype.Service;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogSaveRequestDto;

import java.util.List;

@Service
public interface LogService {

    void save(LogSaveRequestDto logSaveRequestDto);

    LogResponseDto getLog(String logId);

    List<LogResponseDto> getAllLogs();
}
