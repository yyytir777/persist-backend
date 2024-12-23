package yyytir777.persist.domain.category.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.category.entity.Category;

@Getter
@Builder
public class CategoryResponseDto {
    private String categoryId;
    private String name;

    public static CategoryResponseDto of(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
