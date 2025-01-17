package yyytir777.persist.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.entity.QCategory;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Category> findAllByMemberId(Long memberId) {
        QCategory category = QCategory.category;

        return jpaQueryFactory.selectFrom(category)
                .where(category.member.id.eq(memberId))
                .fetch();
    }

    public Optional<Category> findByName(String name) {
        QCategory category = QCategory.category;

        return Optional.ofNullable(jpaQueryFactory.selectFrom(category)
                .where(category.name.eq(name))
                .fetchOne());
    }
}
