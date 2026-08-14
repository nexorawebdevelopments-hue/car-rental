package com.drivelux.car_rental.controller;

import com.drivelux.car_rental.dto.LoginResponse;
import com.drivelux.car_rental.entity.BlogEntity;
import com.drivelux.car_rental.entity.UserEntity;
import com.drivelux.car_rental.services.BlogService;
import com.drivelux.car_rental.services.UserService;
import com.drivelux.car_rental.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/public")
@CrossOrigin("*")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;


//    @PostMapping
//    public DoctorEntity postusers(@RequestBody DoctorEntity doctorEntity) {
//        return doctorService.postusers(doctorEntity);
//    }

//    @GetMapping("/{id}")
//    public DoctorResponse getDoctor(@PathVariable String id) {
//        return doctorService.getDoctor(id);
//    }


    @PostMapping("/signup")
    public UserEntity signup(@RequestBody UserEntity userEntity) {
        return userService.postusersnew(userEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserEntity userEntity) {

        try {

            // 1. Username + password verify
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userEntity.getUsername(),
                            userEntity.getPassword()
                    )
            );

            // 2. Database se complete user nikalo
            UserEntity user = userService.findByUsername(
                    userEntity.getUsername()
            );

            // 3. UserDetails
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(
                            userEntity.getUsername()
                    );

            // 4. JWT mein username + ID + roles
            String jwtToken = jwtUtils.generateToken(
                    userDetails.getUsername(),
                    String.valueOf(user.getId()),
                    user.getRoles()
            );

            // 5. Response
            LoginResponse response =
                    new LoginResponse(user, jwtToken);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }



//get blogs
// 2. Public Open Endpoint to Fetch and List All Blogs (Bina authentication filter check ke)
@GetMapping
public ResponseEntity<List<BlogEntity>> fetchPublicFeed() {
    try {
        List<BlogEntity> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    // Is code function block ko Controller array mappings me shamil karein
    @GetMapping("/{slug}")
    public ResponseEntity<?> getBlogBySlug(@PathVariable String slug) {
        try {
            // Id se hatakar service layer ko custom slug string matrix forward karna
            BlogEntity blog = blogService.getBlogBySlug(slug);
            return new ResponseEntity<>(blog, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server parsing error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}