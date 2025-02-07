package yyytir777.persist.domain.category.dto;

import lombok.Builder;
import lombok.Getter;
import yyytir777.persist.domain.category.entity.Category;

@Getter
@Builder
public class CategoryCreateResponseDto {

    private Long categoryId;
    private String name;

    public static CategoryCreateResponseDto of(Category category) {
        return CategoryCreateResponseDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
