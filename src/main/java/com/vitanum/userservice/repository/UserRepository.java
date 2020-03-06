package com.vitanum.userservice.repository;

import com.vitanum.userservice.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
