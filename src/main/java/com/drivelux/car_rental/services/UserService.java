package com.drivelux.car_rental.services;


import com.drivelux.car_rental.entity.CarEntity;
import com.drivelux.car_rental.entity.UserEntity;
import com.drivelux.car_rental.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity postusers(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    private static final PasswordEncoder passwordencorder = new BCryptPasswordEncoder();

    public UserEntity postusersnew(UserEntity userEntity) {
        userEntity.setPassword(passwordencorder.encode(userEntity.getPassword()));
//        userEntity.setRoles(Arrays.asList("USER"));
        return userRepository.save(userEntity);
    }

    public UserEntity findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    public List<UserEntity> getall() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> getPatient(String userId) {

        UserEntity user = userRepository.findPatientById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }

    public UserEntity updateUserName(String id, UserEntity updatedUser) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUser.getUsername());

        return userRepository.save(user);
    }
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}