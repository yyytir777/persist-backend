package yyytir777.persist.domain.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yyytir777.persist.domain.log.entity.Log;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, String>, LogCustomRepository {

    @Query("SELECT l " +
            "FROM Log l " +
            "JOIN FETCH l.category c " +
            "JOIN FETCH c.member " +
            "WHERE l.category.member.id = :memberId")
    List<Log> findByMemberId(String memberId);

    /**
     * logId로 log -> member까지 가져오기 (지연로딩)
     */
    @Query("SELECT l " +
            "FROM Log l " +
            "JOIN FETCH l.category c " +
            "JOIN FETCH c.member " +
            "WHERE l.id = :logId")
    Optional<Log> findLogAndMemberById(String logId);

    @Transactional
    @Modifying
    @Query("UPDATE Log l " +
            "SET l.viewCount = l.viewCount + 1 " +
            "WHERE l.id = :logId")
    void increaseViewCountByLogId(String logId);

    @Query("SELECT distinct l " +
            "FROM Log l " +
            "JOIN FETCH l.category c " +
            "JOIN FETCH c.member ")
    List<Log> findAllWithMember();

    @Query("SELECT l " +
            "FROM Log l " +
            "WHERE l.category.id = :categoryId ")
    List<Log> findAllByCategoryId(String categoryId);
}
