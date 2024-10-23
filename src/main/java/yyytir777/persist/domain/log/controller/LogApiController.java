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
    public ApiResponse<?> createLog(@RequestBody @Valid LogCreateRequestDto logCreateRequestDto) {
        logService.save(logCreateRequestDto);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "id로 로그 조회")
    @GetMapping("/{log_id}")
    public ApiResponse<LogResponseDto> readLog(@PathVariable(name = "log_id") String logId) {
        LogResponseDto logDto = logService.readLog(logId);
        return ApiResponse.onSuccess(logDto);
    }

    @Operation(summary = "사용자의 모든 로그 조회")
    @GetMapping("/")
    public ApiResponse<List<LogResponseDto>> readLogList() {
        List<LogResponseDto> logResponseDtoList = logService.readAllLogs();
        return ApiResponse.onSuccess(logResponseDtoList);
    }

    @Operation(summary = "로그 수정")
    @PatchMapping("/update/{log_id}")
    public ApiResponse<?> updateLog(@RequestBody @Valid LogUpdateRequestDto logUpdateRequestDto,
                                    @PathVariable(name = "log_id") String logId) {
        logService.update(logUpdateRequestDto, logId);
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "로그 삭제")
    @DeleteMapping("/delete/{log_id}")
    public ApiResponse<?> deleteLog(@PathVariable(name = "log_id") String logId) {
        logService.delete(logId);
        return ApiResponse.onSuccess();
    }
}