package com.example.demo1.controller;

import com.example.demo1.auth.CustomUserDetailsService;

import com.example.demo1.auth.JwtUtil;
import com.example.demo1.dto.Request;
import com.example.demo1.dto.Response;
import com.example.demo1.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServices userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody Request request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getOtp()));

            // Load user details and generate JWT token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new Response(token));
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/generateOtp")
    public String generateOtp(@RequestBody Request request) {
        String otp = userService.generateOtp(request.getEmail());
        return otp;
    }
}

