package com.drivelux.car_rental.controller;

import com.drivelux.car_rental.entity.BlogEntity;
import com.drivelux.car_rental.entity.BookingEntity;
import com.drivelux.car_rental.entity.CarEntity;
import com.drivelux.car_rental.entity.UserEntity;
import com.drivelux.car_rental.services.BlogService;
import com.drivelux.car_rental.services.BookingService;
import com.drivelux.car_rental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") //
public class AdminController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @PutMapping("/bookings/{bookingId}/status")
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
//    this is for blogs
@PostMapping
public ResponseEntity<BlogEntity> createPost(@RequestBody BlogEntity blog) {
    try {
        BlogEntity savedPost = blogService.saveBlogPost(blog);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/signup")
    public UserEntity signup(@RequestBody UserEntity userEntity) {
        return userService.postusersnew(userEntity);
    }

}
