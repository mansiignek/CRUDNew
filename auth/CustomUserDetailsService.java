package com.example.demo1.auth;

import com.example.demo1.model.User;
import com.example.demo1.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.example.demo1.model.User user = userRepository.findByEmail(email);
        if (user != null) {
            String mailFromDb = user.getEmail();
            String roleFromDb = user.getRole();

            if (mailFromDb.equals(email)) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleFromDb));

                return new org.springframework.security.core.userdetails.User(
                        mailFromDb,
                        roleFromDb,
                        authorities
                );
            } else {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}


