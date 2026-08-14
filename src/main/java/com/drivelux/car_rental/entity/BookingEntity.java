package com.drivelux.car_rental.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;

@Data                  // Yeh annotation automatic Getters, Setters, toString, aur equals methods generate karti hai
@NoArgsConstructor     // Default constructor generate karne ke liye
@AllArgsConstructor    // Parameterized constructor generate karne ke liye
@Document(collection = "bookings") // MongoDB collection mapping
public class BookingEntity {

    @Id
    private String id;

    @Field("userId")   // MongoDB column key synchronization tracking map
    private String userId;

    @Field("carId")
    private String carId;

    private LocalDate fromDate;
    private LocalDate toDate;
    private int totalDays;
    private double totalPrice;
    private String status; // PENDING, APPROVED, REJECTED
    private String pickupMethod;
    private String pickupLocation;
    private String customerMobile;

    // NEW: Cloudinary secure URL store karne ke liye field
    private String receiptImage;
}
