package com.drivelux.car_rental.repository;


import com.drivelux.car_rental.entity.CarEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends MongoRepository<CarEntity, String> {

}