package yyytir777.persist.domain.log.repository;

import yyytir777.persist.domain.log.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogCustomRepository {

    List<Log> findByMemberId(Long memberId);

    Optional<Log> findLogAndMemberById(Long logId);

    void increaseViewCountByLogId(Long logId);

    List<Log> findAllWithMember();

    List<Log> findAllByCategoryId(Long categoryId);
}
