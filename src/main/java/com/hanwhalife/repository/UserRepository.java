package com.hanwhalife.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanwhalife.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
