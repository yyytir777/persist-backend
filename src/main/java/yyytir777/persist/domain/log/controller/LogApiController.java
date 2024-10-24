package yyytir777.persist.domain.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yyytir777.persist.domain.log.dto.LogUpdateRequestDto;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.log.service.LogService;
import yyytir777.persist.global.resolver.MemberInfo;
import yyytir777.persist.global.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log")
@Tag(name = "Log API Controller")
public class LogApiController {

    private final LogService logService;

    @Operation(summary = "작성한 로그 저장")
    @PostMapping("/create")
    public ApiResponse<?> createLog(@RequestBody LogCreateRequestDto logCreateRequestDto,
                                    @MemberInfo String memberId) {
        logService.saveLog(logCreateRequestDto, memberId);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "id로 로그 조회")
    @GetMapping("/{log_id}")
    public ApiResponse<LogResponseDto> readLog(@PathVariable(name = "log_id") String logId) {
        return ApiResponse.onSuccess(logService.readLog(logId));
    }

    @Operation(summary = "사용자의 모든 로그 조회")
    @GetMapping("/")
    public ApiResponse<List<LogResponseDto>> readLogList(@MemberInfo String memberId) {
        return ApiResponse.onSuccess(logService.readAllLogs(memberId));
    }

    @Operation(summary = "로그 수정")
    @PatchMapping("/update/{log_id}")
    public ApiResponse<LogResponseDto> updateLog(@RequestBody @Valid LogUpdateRequestDto logUpdateRequestDto,
                                    @PathVariable(name = "log_id") String logId) {
        return ApiResponse.onSuccess(logService.updateLog(logUpdateRequestDto, logId));
    }

    @Operation(summary = "로그 삭제")
    @DeleteMapping("/delete/{log_id}")
    public ApiResponse<?> deleteLog(@PathVariable(name = "log_id") String logId) {
        logService.deleteLog(logId);
        return ApiResponse.onSuccess();
    }
}