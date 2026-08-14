package com.drivelux.car_rental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    // Registration Fields
    @NonNull
    @Indexed(unique = true)
    private String username;

//    private String email;
    private String password;
    private List<String> roles;

//    private CustomerEntity cutomer;

//




}