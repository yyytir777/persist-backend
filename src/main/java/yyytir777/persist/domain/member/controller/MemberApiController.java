package yyytir777.persist.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yyytir777.persist.domain.member.dto.MemberRegisterRequestDto;
import yyytir777.persist.domain.member.dto.MemberResponseDto;
import yyytir777.persist.domain.member.dto.MemberUpdateRequestDto;
import yyytir777.persist.domain.member.service.MemberService;
import yyytir777.persist.global.resolver.MemberId;
import yyytir777.persist.global.resolver.MemberIdDto;
import yyytir777.persist.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member API Controller")
public class MemberApiController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "소셜 로그인으로 이메일을 받아온 후 추가 정보 입력하고 나서의 회원가입")
    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        memberService.register(memberRegisterRequestDto);
        return ApiResponse.onSuccess();
    }

    // TODO 본인을 조회하는지 남의 정보를 조회하는지 구분 필요
    @Operation(summary = "회원 조회")
    @GetMapping("/{member_id}")
    public ApiResponse<MemberResponseDto> readMember(@PathVariable(name = "member_id") Long memberId) {
        return ApiResponse.onSuccess(memberService.readMember(memberId));
    }

    @Operation(summary = "회원 수정", description = "이메일은 변경 불가능")
    @PatchMapping("/update/{member_id}")
    public ApiResponse<MemberResponseDto> updateMember(@MemberId MemberIdDto memberIdDto,
                                                       @RequestBody @Valid MemberUpdateRequestDto memberUpdateRequestDto,
                                                       @PathVariable(name = "member_id") Long memberId) {
        return ApiResponse.onSuccess(memberService.updateMember(memberUpdateRequestDto, memberId, memberIdDto.getMemberId()));
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/delete/{member_id}")
    public ApiResponse<?> deleteMember(@MemberId MemberIdDto memberIdDto,
                                       @PathVariable(name = "member_id") Long memberId) {
        memberService.deleteMember(memberId, memberIdDto.getMemberId());
        return ApiResponse.onSuccess();
    }

    @Operation(summary = "회원의 readme 조회")
    @GetMapping("/readme/{member_id}")
    public ApiResponse<?> getReadme(@PathVariable(name = "member_id") Long memberId) {
        return ApiResponse.onSuccess(memberService.getReadme(memberId));
    }
}
