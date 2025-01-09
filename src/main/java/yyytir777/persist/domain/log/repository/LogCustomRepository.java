package yyytir777.persist.domain.log.repository;

import yyytir777.persist.domain.log.entity.Log;

public interface LogCustomRepository {

    Log findByLogId(String logId);
}
