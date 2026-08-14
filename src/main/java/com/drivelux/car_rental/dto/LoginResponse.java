package com.drivelux.car_rental.dto;

import com.drivelux.car_rental.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private UserEntity user;
    private String token;

}