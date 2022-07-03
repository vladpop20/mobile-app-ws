package com.coodru.mobile.app.ws.io.repository;

import com.coodru.mobile.app.ws.io.entity.AddressEntity;
import com.coodru.mobile.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);


}
