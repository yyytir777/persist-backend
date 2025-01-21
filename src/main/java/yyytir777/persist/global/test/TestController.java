package yyytir777.persist.global.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.response.ApiResponse;
import yyytir777.persist.global.util.SecurityUtil;

@Slf4j
@RestController
@Tag(name = "HealthCheck API Controller")
@RequiredArgsConstructor
public class TestController {

    private final SecurityUtil securityUtil;

    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.onSuccess("success");
    }

    @GetMapping("/health/memberInfo")
    public ApiResponse<Long> healthWithMemberInfo() {
        Long memberId = securityUtil.getCurrentMemberId();
        log.info("memberId ({}) called loginCheck api", memberId);
        return ApiResponse.onSuccess(memberId);
    }
}
