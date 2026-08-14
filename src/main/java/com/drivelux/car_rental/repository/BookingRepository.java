package com.drivelux.car_rental.repository;
import com.drivelux.car_rental.entity.BookingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<BookingEntity, String> {
    // Customer dashboard par user ki bookings dikhane ke liye helper queries
    List<BookingEntity> findByUserId(String userId);
}
