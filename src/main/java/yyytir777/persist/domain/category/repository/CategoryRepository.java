package yyytir777.persist.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.category.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository {

    @Query("select c " +
            "from Category  c " +
            "join fetch c.logList " +
            "where c.id = :categoryId ")
    List<Category> findFetch(Long categoryId);
}
