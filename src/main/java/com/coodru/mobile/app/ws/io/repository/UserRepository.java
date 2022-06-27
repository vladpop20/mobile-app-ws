package com.coodru.mobile.app.ws.io.repository;

		import com.coodru.mobile.app.ws.io.entity.UserEntity;
		import org.springframework.data.repository.CrudRepository;
		import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	//	UserEntity findUserEntityByEmail (String email);

	UserEntity findByEmail (String email);

	UserEntity findByUserId(String userId);
}
