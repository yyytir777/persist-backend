package yyytir777.persist.domain.log.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yyytir777.persist.domain.log.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogCustomRepository {

    List<Log> findByMemberId(Long memberId);

    Optional<Log> findLogAndMemberById(Long logId);

    void increaseViewCountByLogId(Long logId);

    Page<Log> findAllWithMember(Pageable pageable);

    List<Log> findAllByCategoryId(Long categoryId);
}
