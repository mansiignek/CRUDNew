package com.example.demo1.serviceImpl;

import com.example.demo1.auth.OTPUtils;
import com.example.demo1.model.User;
import com.example.demo1.repo.UserRepository;
import com.example.demo1.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
    public class UserServiceImpl implements UserServices {
            @Autowired
            private UserRepository userRepository;
            @Autowired
            private OTPUtils otpUtils;
            @Override
            public User createUser(User user) {
                return userRepository.save(user);
            }

            @Override
            public User getUserById(Long id) {
                return userRepository.findById(id).orElse(null);
            }
             @Override
                public User updateUser(Long id, User updatedUser) throws Exception {
                User user = userRepository.findById(id).orElse(null);
                if (user != null) {
                    user.setEmail(updatedUser.getEmail());
                    user.setRole(updatedUser.getRole());
                    return userRepository.save(user);
                }
               throw new Exception("user not found");
            }
            @Override
            public void deleteUser(Long id) {
                userRepository.deleteById(id);
            }
            @Override
            public String generateOtp(String email) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw  new RuntimeException("user not found");
            }
            String otp = generateRandomOtp();
                String hashedOtp =otpUtils.hash(otp);
                user.setOtp(hashedOtp);
                userRepository.save(user);


                return otp;
        }

        private String generateRandomOtp() {
                Random random = new Random();
                int randomNumber = random.nextInt(9000) + 1000;
                return String.valueOf(randomNumber);
            }
        }





