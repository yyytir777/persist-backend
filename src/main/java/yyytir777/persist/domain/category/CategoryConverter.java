package yyytir777.persist.domain.category;

import yyytir777.persist.domain.category.dto.CategoryCreateRequestDto;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.member.entity.Member;

public class CategoryConverter {

    public static Category CreateRequestToEntity(CategoryCreateRequestDto categoryCreateRequestDto, Member member) {

        return Category.builder()
                .name(categoryCreateRequestDto.getName())
                .member(member)
                .build();
    }
}
