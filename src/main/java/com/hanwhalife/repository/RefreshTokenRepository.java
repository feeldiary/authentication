package com.hanwhalife.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanwhalife.entity.JwtRefreshToken;
import com.sun.xml.bind.v2.model.core.ID;

@Repository
public interface RefreshTokenRepository extends CrudRepository<JwtRefreshToken, ID> {

	@Override
	default <S extends JwtRefreshToken> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	JwtRefreshToken findByUserIdAndToken(String userId, String token);
}
