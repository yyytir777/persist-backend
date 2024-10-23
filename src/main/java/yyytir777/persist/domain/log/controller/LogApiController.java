package yyytir777.persist.domain.log.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yyytir777.persist.domain.log.dto.LogResponseDto;
import yyytir777.persist.domain.log.dto.LogSaveRequestDto;
import yyytir777.persist.domain.log.service.LogService;
import yyytir777.persist.global.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log")
public class LogApiController {

    private final LogService logService;

    /**
     * 로그 조회
     * @param logId
     * @return
     */
    @GetMapping("/{log_id}")
    public ApiResponse<LogResponseDto> getLog(@PathVariable(name = "log_id") String logId) {
        LogResponseDto logDto = logService.getLog(logId);
        return ApiResponse.onSuccess(logDto);
    }

    /**
     * 사용자의 모든 로그 조회
     * @return
     */
    @GetMapping("/")
    public ApiResponse<List<LogResponseDto>> getLogList() {
        List<LogResponseDto> logResponseDtoList = logService.getAllLogs();
        return ApiResponse.onSuccess(logResponseDtoList);
    }


    /**
     * 로그 저장
     * @param logSaveRequestDto
     * @return
     */
    @PostMapping("/save")
    public ApiResponse<?> saveLog(@RequestBody @Valid LogSaveRequestDto logSaveRequestDto) {
        logService.save(logSaveRequestDto);
        return ApiResponse.onSuccess("");
    }

}