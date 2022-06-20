package com.coodru.mobile.app.ws.repository;

import com.coodru.mobile.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findUserEntityByEmail (String email);
}
