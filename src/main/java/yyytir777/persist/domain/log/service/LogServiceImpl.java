package yyytir777.persist.domain.log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
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

    @Transactional
    public LogResponseDto readLog(String logId) {
        Log findLog = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));
        return LogResponseDto.of(findLog, findLog.getMember());
    }

    @Transactional
    public List<LogResponseDto> readAllLogs() {
        return logRepository.findAll().stream()
                .map(log -> LogResponseDto.of(log, log.getMember()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LogResponseDto> readAllLogsByMemberId(String memberId) {
        return logRepository.findByMemberId(memberId).stream()
                .map(log -> LogResponseDto.of(log, log.getMember()))
                .toList();
    }

    public LogResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, String logId, String memberId) {
        Log log = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        String logMemberId = log.getMember().getId();
        if(!memberId.equals(logMemberId)) throw new LogException(ErrorCode.NOT_MY_LOG);

        Member member = memberRepository.findById(logMemberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        log = Log.builder()
                .id(logId)
                .member(member)
                .title(logUpdateRequestDto.getTitle())
                .thumbnail(logUpdateRequestDto.getThumbnail())
                .content(logUpdateRequestDto.getContent())
                .build();

        logRepository.save(log);
        return LogResponseDto.of(log, log.getMember());
    }

    public void deleteLog(String logId, String memberId) {
        Log log = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        String logMemberId = log.getMember().getId();

        if(!memberId.equals(logMemberId)) throw new LogException(ErrorCode.NOT_MY_LOG);

        logRepository.deleteById(logId);
    }
}
