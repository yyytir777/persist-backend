package yyytir777.persist.global.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.domain.member.service.MemberService;
import yyytir777.persist.global.response.ApiResponse;
import yyytir777.persist.global.util.SecurityUtil;

@Slf4j
@RestController
@Tag(name = "HealthCheck API Controller")
@RequiredArgsConstructor
public class TestController {

    private final SecurityUtil securityUtil;
    private final MemberService memberService;

    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.onSuccess("success");
    }

    @GetMapping("/health/memberInfo")
    public ApiResponse<Long> healthWithMemberInfo() {
        Long memberId = securityUtil.getCurrentMemberId();
        String email = memberService.findById(memberId).getEmail();
        log.info("{} called loginCheck api", email);
        return ApiResponse.onSuccess(memberId);
    }
}
