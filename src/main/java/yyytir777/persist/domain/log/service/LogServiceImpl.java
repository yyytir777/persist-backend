package yyytir777.persist.domain.log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yyytir777.persist.domain.log.dto.LogDetailResponseDto;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.log.dto.LogThumbnailResponseDto;
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

    public String saveLog(LogCreateRequestDto logCreateRequestDto, String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        Log log = Log.builder()
                .member(member)
                .title(logCreateRequestDto.getTitle())
                .thumbnail(logCreateRequestDto.getThumbnail())
                .content(logCreateRequestDto.getContent())
                .preview(logCreateRequestDto.getContent().substring(0, Math.min(logCreateRequestDto.getContent().length(), 30)))
                .viewCount(0)
                .build();

        logRepository.save(log);
        return log.getId();
    }

    /**
     * 로그를 읽어들이고 viewCount 증가
     */
    public LogDetailResponseDto readLog(String logId, boolean hasViewed) {
        Log findLog = logRepository.findLogAndMemberById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        if(!hasViewed) logRepository.increaseViewCountByLog(findLog);

        return LogDetailResponseDto.of(findLog);
    }

    @Transactional(readOnly = true)
    public List<LogThumbnailResponseDto> readAllLogs() {
        return logRepository.findAllWithMember().stream()
                .map(LogThumbnailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LogThumbnailResponseDto> readAllLogsByMemberId(String memberId) {
        return logRepository.findByMemberId(memberId).stream()
                .map(LogThumbnailResponseDto::of)
                .toList();
    }

    public LogThumbnailResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, String logId, String memberId) {
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
        return LogThumbnailResponseDto.of(log);
    }

    public void deleteLog(String logId, String memberId) {
        Log log = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        String logMemberId = log.getMember().getId();

        if(!memberId.equals(logMemberId)) throw new LogException(ErrorCode.NOT_MY_LOG);

        logRepository.deleteById(logId);
    }
}
