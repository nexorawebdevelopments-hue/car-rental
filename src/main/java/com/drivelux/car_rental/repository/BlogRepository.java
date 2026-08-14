package com.drivelux.car_rental.repository;


import com.drivelux.car_rental.entity.BlogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BlogRepository extends MongoRepository<BlogEntity, String> {
    // Fronted standard lookup optimization utility
    Optional<BlogEntity> findBySlug(String slug);
}
