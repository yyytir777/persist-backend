package yyytir777.persist.domain.log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.repository.CategoryRepository;
import yyytir777.persist.domain.log.LogConverter;
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
    private final CategoryRepository categoryRepository;

    private final String defaultCategoryName = "Default";

    /*
    log를 저장하는 로직
    Category가 살아있다면
     */
    public Long saveLog(LogCreateRequestDto logCreateRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        String categoryName = logCreateRequestDto.getCategoryName();
        Category category = findOrCreateCategoryOfLog(categoryName, member);

        Log log = LogConverter.CreateRequestToEntity(logCreateRequestDto, category);

        logRepository.save(log);
        return log.getId();
    }

    /*
    categoryName이 존재하면 해당 category를 찾음 (없으면 생성)
    categoryName이 존재하지 않으면 기본 category를 사용
     */
    private Category findOrCreateCategoryOfLog(String categoryName, Member member) {
        if(categoryName != null) {
            return categoryRepository.findByName(categoryName).orElseGet(() ->
                    createAndSaveCategory(categoryName, member));
        }

        return categoryRepository.findByName(defaultCategoryName).orElseGet(() ->
                createAndSaveCategory(defaultCategoryName, member));
    }

    /*
    category생성하여 반환
     */
    private Category createAndSaveCategory(String categoryName, Member member) {
        return categoryRepository.save(Category.builder()
                .name(categoryName)
                .member(member)
                .build());
    }

    public LogDetailResponseDto readLog(Long logId, boolean hasViewed) {

        // 게시글 조회가 유효하지 않다면 조회수 증가
        if(!hasViewed) logRepository.increaseViewCountByLogId(logId);

        Log findLog = logRepository.findLogAndMemberById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        return LogDetailResponseDto.of(findLog);
    }

    @Transactional(readOnly = true)
    public List<LogThumbnailResponseDto> readAllLogs() {
        return logRepository.findAllWithMember().stream()
                .map(LogThumbnailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LogThumbnailResponseDto> readAllLogsByMemberId(Long memberId) {
        return logRepository.findByMemberId(memberId).stream()
                .map(LogThumbnailResponseDto::of)
                .toList();
    }

    public LogDetailResponseDto updateLog(LogUpdateRequestDto logUpdateRequestDto, Long logId, Long memberId) {
        Log log = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        Long logMemberId = log.getCategory().getMember().getId();
        if(!memberId.equals(logMemberId)) throw new LogException(ErrorCode.NOT_MY_LOG);

        String categoryName = logUpdateRequestDto.getCategoryName();
        Category category = findOrCreateCategoryOfLog(categoryName, log.getCategory().getMember());

        log = LogConverter.UpdateRequestToEntity(logUpdateRequestDto, logId, category, log.getViewCount());

        logRepository.save(log);
        return LogDetailResponseDto.of(log);
    }


    public void deleteLog(Long logId, Long memberId) {
        Log log = logRepository.findById(logId).orElseThrow(() ->
                new LogException(ErrorCode.LOG_NOT_EXIST));

        Long logMemberId = log.getCategory().getMember().getId();

        if(!memberId.equals(logMemberId)) throw new LogException(ErrorCode.NOT_MY_LOG);

        logRepository.deleteById(logId);
    }
}
