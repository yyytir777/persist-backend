package yyytir777.persist.domain.member.repository;

import java.util.Optional;

public interface MemberCustomRepository {

    Optional<String> findReadmeById(String memberId);
}
