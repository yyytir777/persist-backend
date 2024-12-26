package yyytir777.persist.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmail(String email);

    @Query("select m.readme " +
            "from Member m " +
            "where m.id = :memberId")
    String findReadmeById(String memberId);
}
