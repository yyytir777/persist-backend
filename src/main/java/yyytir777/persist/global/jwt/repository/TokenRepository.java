package yyytir777.persist.global.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yyytir777.persist.global.jwt.RefreshToken;

@Repository
public interface TokenRepository extends CrudRepository<RefreshToken, Long> {

}
