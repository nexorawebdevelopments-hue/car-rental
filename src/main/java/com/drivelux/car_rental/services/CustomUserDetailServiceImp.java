package com.drivelux.car_rental.services;

import com.drivelux.car_rental.entity.UserEntity;
import com.drivelux.car_rental.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailServiceImp implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException(
                    "user not found with username: " + username);
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())   // BCrypt
//                .roles("USER")                  // Temporary rakha hoa jab role nahi tha
                .roles(user.getRoles().toArray(new String[0])) // later on i added this
                .build();

    }
}
