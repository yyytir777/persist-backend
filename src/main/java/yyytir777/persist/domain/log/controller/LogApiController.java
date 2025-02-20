package yyytir777.persist.domain.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import yyytir777.persist.domain.log.dto.LogDetailResponseDto;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogThumbnailResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.log.service.LogService;
import yyytir777.persist.domain.log.service.ViewCountValidator;
import yyytir777.persist.global.response.ApiResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log")
@Tag(name = "Log API Controller")
public class LogApiController {

    private final LogService logService;
    private final ViewCountValidator viewCountValidator;

    @Operation(summary = "작성한 로그 저장")
    @PostMapping("/create")
    public ApiResponse<Long> createLog(@RequestBody LogCreateRequestDto logCreateRequestDto) {
        Long logId = logService.saveLog(logCreateRequestDto);
        return ApiResponse.onSuccess(logId);
    }

    // TODO 로그 조회시 본인의 로그인지 체크 필요
    @Operation(summary = "id로 로그 조회")
    @GetMapping("/{log_id}")
    public ApiResponse<LogDetailResponseDto> readLog(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "log_id") Long logId) {
        boolean hasViewed = viewCountValidator.hasViewedInCoookie(request, response, logId);
        return ApiResponse.onSuccess(logService.readLog(logId, hasViewed));
    }

    @Operation(summary = "사용자의 모든 로그 조회")
    @GetMapping("/member/{member_id}")
    public ApiResponse<List<LogThumbnailResponseDto>> getAllLogsByMemberId(@PathVariable(name = "member_id") Long memberId) {
        return ApiResponse.onSuccess(logService.readAllLogsByMemberId(memberId));
    }

    @Operation(summary = "모든 로그 조회")
    @GetMapping("/all")
    public ApiResponse<Page<LogThumbnailResponseDto>> readAllLog(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess(logService.readAllLogs(page, size));
    }

    @Operation(summary = "로그 수정")
    @PatchMapping("/update/{log_id}")
    public ApiResponse<LogDetailResponseDto> updateLog(@RequestBody @Valid LogUpdateRequestDto logUpdateRequestDto,
                                                       @PathVariable(name = "log_id") Long logId) {
        return ApiResponse.onSuccess(logService.updateLog(logUpdateRequestDto, logId));
    }

    @Operation(summary = "로그 삭제")
    @DeleteMapping("/delete/{log_id}")
    public ApiResponse<?> deleteLog(@PathVariable(name = "log_id") Long logId) {
        logService.deleteLog(logId);
        return ApiResponse.onSuccess();
    }
}