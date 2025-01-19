package yyytir777.persist.global.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yyytir777.persist.global.resolver.memberId.MemberId;
import yyytir777.persist.global.resolver.memberId.MemberIdDto;
import yyytir777.persist.global.response.ApiResponse;

@Slf4j
@RestController
@Tag(name = "HealthCheck API Controller")
public class TestController {

    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.onSuccess("success");
    }

    @GetMapping("/health/memberInfo")
    public ApiResponse<Long> healthWithMemberInfo(@MemberId MemberIdDto memberIdDto) {
        Long memberId = memberIdDto.getMemberId();
        log.info("memberId ({}) called loginCheck api", memberId);
        return ApiResponse.onSuccess(memberId);
    }
}
