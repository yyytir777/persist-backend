package yyytir777.persist.domain.log.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.category.entity.QCategory;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.entity.QLog;
import yyytir777.persist.domain.member.entity.QMember;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LogCustomRepositoryImpl implements LogCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;


    public List<Log> findByMemberId(Long memberId) {
        QLog log = QLog.log;
        QMember member = QMember.member;

        return jpaQueryFactory.selectFrom(log)
                .join(log.category.member, member)
                .where(member.id.eq(memberId))
                .fetch();
    }

    public Optional<Log> findLogAndMemberById(Long logId) {
        QLog log = QLog.log;
        QMember member = QMember.member;
        QCategory category = QCategory.category;

        Log result = jpaQueryFactory.selectFrom(log)
                .join(log.category, category).fetchJoin()
                .join(category.member, member).fetchJoin()
                .where(log.id.eq(logId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public void increaseViewCountByLogId(Long logId) {
        QLog log = QLog.log;

        jpaQueryFactory.update(log)
                .set(log.viewCount, log.viewCount.add(1))
                .where(log.id.eq(logId))
                .execute();
    }

    public List<Log> findAllWithMember() {
        QLog log = QLog.log;
        QCategory category = QCategory.category;
        QMember member = QMember.member;

        return jpaQueryFactory.selectFrom(log)
                .join(log.category, category).fetchJoin()
                .join(log.category.member, member).fetchJoin()
                .fetch();
    }

    public List<Log> findAllByCategoryId(Long categoryId) {
        QLog log = QLog.log;

        return jpaQueryFactory.selectFrom(log)
                .where(log.category.id.eq(categoryId))
                .fetch();
    }
}
