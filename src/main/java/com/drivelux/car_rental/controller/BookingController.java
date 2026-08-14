package com.drivelux.car_rental.controller;

import com.drivelux.car_rental.entity.BookingEntity;

import com.drivelux.car_rental.entity.CarEntity;
import com.drivelux.car_rental.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@CrossOrigin("*") // Angular stream ports access configuration bypass
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PutMapping("/{bookingId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String bookingId,
            @RequestBody Map<String, String> requestBody) {
        try {
            // Frontend se aane wali JSON key "status" ko direct string me extract karna
            String status = requestBody.get("status");

            BookingEntity updatedBooking = bookingService.updateBookingStatus(bookingId, status);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Angular service call handler mapping matching: post('/car/book', payload)
//    @PostMapping("/car/book")
//    public ResponseEntity<BookingEntity> createBooking(@RequestBody BookingEntity booking) {
//        try {
//            BookingEntity newBooking = bookingService.saveBooking(booking);
//            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<BookingEntity> bookCar(

            @RequestParam String userId,
            @RequestParam String carId,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam int totalDays,
            @RequestParam double totalPrice,
            @RequestParam String pickupMethod,
            @RequestParam String pickupLocation,
            @RequestParam String customerMobile,
            @RequestParam String status,
            @RequestParam("receiptImage") MultipartFile file

    ) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        BookingEntity booking = new BookingEntity();

        booking.setUserId(userId);
        booking.setCarId(carId);
        booking.setFromDate(fromDate);
        booking.setToDate(toDate);
        booking.setTotalDays(totalDays);
        booking.setTotalPrice(totalPrice);
        booking.setPickupMethod(pickupMethod);
        booking.setPickupLocation(pickupLocation);
        booking.setCustomerMobile(customerMobile);
        booking.setStatus(status);

        BookingEntity saved = bookingService.saveWithReceipt(
                booking,
                file
        );

        return ResponseEntity.ok(saved);
    }
    // Customer page context logic optimization: See Bookings feature
    @GetMapping("/bookings/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable String userId) {
        try {
            List<BookingEntity> userBookings = bookingService.getBookingsByUserId(userId);
            return new ResponseEntity<>(userBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<BookingEntity> getAllCars() {
        return bookingService.getAllCars();
    }
}
