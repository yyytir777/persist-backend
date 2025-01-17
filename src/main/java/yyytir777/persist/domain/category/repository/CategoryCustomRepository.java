package yyytir777.persist.domain.category.repository;

import yyytir777.persist.domain.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryCustomRepository {

    List<Category> findAllByMemberId(Long memberId);

    Optional<Category> findByName(String name);
}
