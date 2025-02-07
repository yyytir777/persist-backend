package yyytir777.persist.global.jwtToken.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yyytir777.persist.global.jwtToken.dto.RefreshToken;

@Repository
public interface JwtTokenRepository extends CrudRepository<RefreshToken, Long> {

}
