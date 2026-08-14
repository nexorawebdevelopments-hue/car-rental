package com.drivelux.car_rental.repository;

import com.drivelux.car_rental.entity.UserEntity;
//import com.medicare.backend.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByUsername(String name);


    Optional<UserEntity> findPatientById(String id);
//    List<UserEntity> findByRolesContainingAndDoctorIsNotNull(String role);
}