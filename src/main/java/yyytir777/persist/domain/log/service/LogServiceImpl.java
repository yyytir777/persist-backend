package yyytir777.persist.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.LogException;
import yyytir777.persist.global.error.exception.MemberException;
import yyytir777.persist.global.resolver.MemberInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final MemberRepository memberRepository;

    public void saveLog(LogCreateRequestDto logCreateRequestDto, String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        Log log = Log.builder()
                .member(member)
                .title(logCreateRequestDto.getTitle())
                .thumbnail(logCreateRequestDto.getThumbnail())
                .content(logCreateRequestDto.getContent())
                .build();

        logRepository.save(log);
    }

    public LogResponseDto readLog(String logId) {
        Log findLog = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));
        return LogResponseDto.of(findLog);
    }

    public List<LogResponseDto> readAllLogs(@MemberInfo String memberId) {
        return logRepository.findByMemberId(memberId).stream()
                .map(LogResponseDto::of)
                .toList();
    }

    public LogResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, String logId) {
        Log log = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));
        String memberId = log.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        log = Log.builder()
                .id(logId)
                .member(member)
                .title(logUpdateRequestDto.getTitle())
                .thumbnail(logUpdateRequestDto.getThumbnail())
                .content(logUpdateRequestDto.getContent())
                .build();

        logRepository.save(log);
        return LogResponseDto.of(log);
    }

    public void deleteLog(String logId) {
        logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));
        logRepository.deleteById(logId);
    }
}
