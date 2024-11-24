package yyytir777.persist.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.category.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE c.member.id = :memberId ")
    List<Category> findAllByMemberId(String memberId);

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE c.name = :name ")
    Optional<Category> findByName(String name);
}
