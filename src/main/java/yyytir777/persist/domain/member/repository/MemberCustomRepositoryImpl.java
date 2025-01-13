package yyytir777.persist.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.member.entity.QMember;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<String> findReadmeById(Long memberId) {
        QMember member = QMember.member;
        String result = jpaQueryFactory
                .select(member.readme)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
