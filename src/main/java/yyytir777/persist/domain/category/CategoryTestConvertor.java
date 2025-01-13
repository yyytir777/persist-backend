package yyytir777.persist.domain.category;

import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.member.entity.Member;

public class CategoryTestConvertor {

    public static Category createCategoryInTest(Long categoryId, Member member) {
        return new Category(categoryId, member);
    }
}
