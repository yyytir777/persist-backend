package yyytir777.persist.domain.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yyytir777.persist.domain.log.entity.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

    @Query("SELECT l " +
            "FROM Log l " +
            "JOIN FETCH l.member " +
            "WHERE l.member.Id = :memberId")
    List<Log> findByMemberId(String memberId);

    /**
     * logId로 log -> member까지 가져오기 (지연로딩)
     * @param logId
     * @return
     */
    @Query("SELECT l " +
            "FROM Log l " +
            "JOIN FETCH l.member " +
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
            "JOIN FETCH l.member ")
    List<Log> findAllWithMember();
}
