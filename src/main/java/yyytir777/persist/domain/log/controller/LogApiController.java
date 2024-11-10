package yyytir777.persist.domain.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yyytir777.persist.domain.log.dto.LogDetailResponseDto;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogThumbnailResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.log.service.LogService;
import yyytir777.persist.global.resolver.MemberId;
import yyytir777.persist.global.resolver.MemberIdDto;
import yyytir777.persist.global.response.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log")
@Tag(name = "Log API Controller")
public class LogApiController {

    private final LogService logService;

    @Operation(summary = "작성한 로그 저장")
    @PostMapping("/create")
    public ApiResponse<?> createLog(@MemberId MemberIdDto memberIdDto,
                                    @RequestBody LogCreateRequestDto logCreateRequestDto) {
        logService.saveLog(logCreateRequestDto, memberIdDto.getMemberId());
        return ApiResponse.onSuccess();
    }

    // TODO 로그 조회시 본인의 로그인지 체크 필요
    @Operation(summary = "id로 로그 조회")
    @GetMapping("/{log_id}")
    public ApiResponse<LogDetailResponseDto> readLog(@PathVariable(name = "log_id") String logId) {
        return ApiResponse.onSuccess(logService.readLog(logId));
    }

    @Operation(summary = "사용자의 모든 로그 조회")
    @GetMapping("/member/{member_id}")
    public ApiResponse<List<LogThumbnailResponseDto>> getAllLogsByMemberId(@PathVariable(name = "member_id") String memberId) {
        return ApiResponse.onSuccess(logService.readAllLogsByMemberId(memberId));
    }

    @Operation(summary = "모든 로그 조회")
    @GetMapping("/all")
    public ApiResponse<List<LogThumbnailResponseDto>> readAllLog() {
        return ApiResponse.onSuccess(logService.readAllLogs());
    }

    @Operation(summary = "로그 수정")
    @PatchMapping("/update/{log_id}")
    public ApiResponse<LogThumbnailResponseDto> updateLog(@MemberId MemberIdDto memberIdDto,
                                                          @RequestBody @Valid LogUpdateRequestDto logUpdateRequestDto,
                                                          @PathVariable(name = "log_id") String logId) {
        return ApiResponse.onSuccess(logService.updateLog(logUpdateRequestDto, logId,memberIdDto.getMemberId()));
    }

    @Operation(summary = "로그 삭제")
    @DeleteMapping("/delete/{log_id}")
    public ApiResponse<?> deleteLog(@MemberId MemberIdDto memberIdDto,
                                    @PathVariable(name = "log_id") String logId) {
        logService.deleteLog(logId, memberIdDto.getMemberId());
        return ApiResponse.onSuccess();
    }
}