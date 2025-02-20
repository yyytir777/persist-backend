package yyytir777.persist.domain.category;

import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.member.entity.Member;

public class CategoryTestConverter {

    public static Category createCategoryInTest(Long categoryId, Member member, String name) {
        return new Category(categoryId, member, name);
    }

    public static Category createCategoryInTest(Long categoryId, Member member) {
        return new Category(categoryId, member);
    }

    public static Category createCategoryInTest(Member member) {
        return new Category(member);
    }
}
