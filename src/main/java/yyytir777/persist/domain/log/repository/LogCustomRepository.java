package yyytir777.persist.domain.log.repository;

import yyytir777.persist.domain.log.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogCustomRepository {

    List<Log> findByMemberId(String memberId);

    Optional<Log> findLogAndMemberById(String logId);

    void increaseViewCountByLogId(String logId);

    List<Log> findAllWithMember();

    List<Log> findAllByCategoryId(String categoryId);
}
