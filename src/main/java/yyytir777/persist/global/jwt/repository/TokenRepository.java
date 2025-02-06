package yyytir777.persist.global.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import yyytir777.persist.global.jwt.RefreshToken;

public interface TokenRepository extends CrudRepository<RefreshToken, Long> {

}
