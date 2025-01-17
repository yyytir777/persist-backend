package yyytir777.persist.domain.category.service;

import yyytir777.persist.domain.category.dto.CategoryCreateRequestDto;
import yyytir777.persist.domain.category.dto.CategoryResponseDto;
import yyytir777.persist.domain.category.dto.CategoryUpdateRequestDto;
import yyytir777.persist.domain.category.entity.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(CategoryCreateRequestDto categoryCreateRequestDto, Long memberId);

    List<CategoryResponseDto> getAllCategory(Long memberId);

    Category updateCategory(Long memberId, Long categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategory(Long memberId, Long categoryId);
}
