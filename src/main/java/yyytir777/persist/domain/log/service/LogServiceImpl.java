package yyytir777.persist.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.member.Member;
import yyytir777.persist.domain.member.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogRepository logRepository;
    private final MemberRepository memberRepository;

    public void save(LogCreateRequestDto logCreateRequestDto) {
        Log log = Log.builder()
                .member(memberRepository.findByName("demo").orElseThrow())
                .title(logCreateRequestDto.getTitle())
                .thumbnail(logCreateRequestDto.getThumbnail())
                .content(logCreateRequestDto.getContent())
                .build();

        logRepository.save(log);
    }

    public LogResponseDto readLog(String logId) {
        Log findLog = logRepository.findById(logId).orElseThrow();
        return LogResponseDto.of(findLog);
    }

    public List<LogResponseDto> readAllLogs() {
        String memberId = memberRepository.findByName("demo").orElseThrow().getId();

        return logRepository.findByMemberId(memberId).stream()
                .map(LogResponseDto::of)
                .toList();
    }

    public void update(LogUpdateRequestDto logUpdateRequestDto, String logId) {
        Log log = logRepository.findById(logId).orElseThrow();
        String memberId = log.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow();

        log = Log.builder()
                .id(logId)
                .member(member)
                .title(logUpdateRequestDto.getTitle())
                .thumbnail(logUpdateRequestDto.getThumbnail())
                .content(logUpdateRequestDto.getContent())
                .build();

        logRepository.save(log);
    }

    public void delete(String logId) {
        logRepository.deleteById(logId);
    }
}
