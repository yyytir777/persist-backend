package yyytir777.persist.domain.category.service;

import yyytir777.persist.domain.category.dto.CategoryCreateRequestDto;
import yyytir777.persist.domain.category.dto.CategoryResponseDto;
import yyytir777.persist.domain.category.dto.CategoryUpdateRequestDto;
import yyytir777.persist.domain.category.entity.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(CategoryCreateRequestDto categoryCreateRequestDto, String memberId);

    List<CategoryResponseDto> getAllCategory(String memberId);

    Category updateCategory(String memberId, String categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategory(String memberId, String categoryId);
}
