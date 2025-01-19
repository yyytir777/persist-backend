package yyytir777.persist.domain.category.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yyytir777.persist.domain.category.dto.CategoryCreateRequestDto;
import yyytir777.persist.domain.category.dto.CategoryCreateResponseDto;
import yyytir777.persist.domain.category.dto.CategoryResponseDto;
import yyytir777.persist.domain.category.dto.CategoryUpdateRequestDto;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.service.CategoryService;
import yyytir777.persist.global.resolver.memberId.MemberId;
import yyytir777.persist.global.resolver.memberId.MemberIdDto;
import yyytir777.persist.global.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Tag(name = "Category API Controller")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ApiResponse<CategoryCreateResponseDto> createCategory(@MemberId MemberIdDto memberIdDto,
                                                                 @RequestBody CategoryCreateRequestDto categoryCreateRequestDto) {
        Category category = categoryService.saveCategory(categoryCreateRequestDto, memberIdDto.getMemberId());
        CategoryCreateResponseDto categoryCreateResponseDto = CategoryCreateResponseDto.of(category);
        return ApiResponse.onSuccess(categoryCreateResponseDto);
    }

    @GetMapping("/all")
    public ApiResponse<List<CategoryResponseDto>> getCategoryList(@MemberId MemberIdDto memberIdDto) {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategory(memberIdDto.getMemberId());
        return ApiResponse.onSuccess(categoryResponseDtoList);
    }

    @PatchMapping("/update/{category_id}")
    public ApiResponse<CategoryResponseDto> updateCategory(@MemberId MemberIdDto memberIdDto,
                                                           @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto,
                                                           @PathVariable(name = "category_id") Long categoryId) {
        Category category = categoryService.updateCategory(memberIdDto.getMemberId(), categoryId, categoryUpdateRequestDto);
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.of(category);
        return ApiResponse.onSuccess(categoryResponseDto);
    }

    @DeleteMapping("/delete/{category_id}")
    public ApiResponse<?> deleteCategory(@MemberId MemberIdDto memberIdDto,
                                         @PathVariable(name = "category_id") Long categoryId) {
        categoryService.deleteCategory(memberIdDto.getMemberId(), categoryId);
        return ApiResponse.onSuccess();
    }
}
