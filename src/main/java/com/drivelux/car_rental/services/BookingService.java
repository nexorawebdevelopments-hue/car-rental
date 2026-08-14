package com.drivelux.car_rental.services;

import com.cloudinary.Cloudinary;
import com.drivelux.car_rental.entity.BookingEntity;
import com.drivelux.car_rental.entity.CarEntity;
import com.drivelux.car_rental.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class BookingService {
@Autowired
private Cloudinary cloudinary;
    @Autowired
    private BookingRepository bookingRepository;

//    public BookingEntity saveBooking(BookingEntity booking) {
//        // Hamesha ensure karein ke new booking initial state mein 'PENDING' ho
//        if (booking.getStatus() == null) {
//            booking.setStatus("PENDING");
//        }
//        return bookingRepository.save(booking);
//    }
public BookingEntity saveWithReceipt(
        BookingEntity booking,
        MultipartFile file
) {

    try {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Receipt is required");
        }

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of("folder", "booking-receipts")
        );

        booking.setReceiptImage(
                (String) uploadResult.get("secure_url")
        );

        return bookingRepository.save(booking);

    } catch (Exception e) {

        throw new RuntimeException(
                "Failed to save booking",
                e
        );
    }
}

    public List<BookingEntity> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }
//    for admin update status ka lia
public BookingEntity updateBookingStatus(String bookingId, String newStatus) {
    // 1. Repository call: Pehle id dhoodo database se
    BookingEntity booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

    // 2. State shift logic execution
    booking.setStatus(newStatus.toUpperCase());

    // 3. Repository call: Database me wapas save karo data maps rows update pipeline ko
    return bookingRepository.save(booking);
}
    public List<BookingEntity> getAllCars() {
        return bookingRepository.findAll();
    }
}
