package yyytir777.persist.domain.log.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.entity.QLog;

@Repository
@RequiredArgsConstructor
public class LogCustomRepositoryImpl implements LogCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Log findByLogId(String logId) {
        QLog log = QLog.log;
        return jpaQueryFactory
                .selectFrom(log)
                .where(log.id.eq(logId))
                .fetchOne();
    }
}
