package yyytir777.persist.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogSaveRequestDto;
import yyytir777.persist.domain.member.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogRepository logRepository;
    private final MemberRepository memberRepository;

    public void save(LogSaveRequestDto logSaveRequestDto) {
        Log log = Log.builder()
                .member(memberRepository.findByName("demo").orElseThrow())
                .title(logSaveRequestDto.getTitle())
                .thumbnail(logSaveRequestDto.getThumbnail())
                .content(logSaveRequestDto.getContent())
                .build();

        logRepository.save(log);
    }

    public LogResponseDto getLog(String logId) {
        Log findLog = logRepository.findById(logId).orElseThrow();
        return LogResponseDto.of(findLog);
    }

    public List<LogResponseDto> getAllLogs() {
        String memberId = memberRepository.findByName("demo").orElseThrow().getId();

        return logRepository.findByMemberId(memberId).stream()
                .map(LogResponseDto::of)
                .toList();
    }
}
