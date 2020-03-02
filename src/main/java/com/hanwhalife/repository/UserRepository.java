package com.hanwhalife.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanwhalife.entity.UserInfo;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {
//
//	@Override
//	default <S extends User> S save(S entity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	default Optional<User> findById(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
	
	List<UserInfo> findByUserNm(String userNm);

	List<UserInfo> findByUserId(String userId);
	
	UserInfo findByUserIdAndPassword(String userId, String password);
}
