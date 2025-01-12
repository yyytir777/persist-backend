package yyytir777.persist.domain.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yyytir777.persist.domain.log.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, String>, LogCustomRepository {
}
