package com.kritica.security.service;

import com.kritica.model.Users;
import com.kritica.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    //Spring Security will call this method to load the user details
    // from the database
    // once the user login request is coming to server

    private UsersRepository usersRepository;
    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository= usersRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Users user =  usersRepository.findByUsername(username)
                    .orElseThrow(()->
                            new UsernameNotFoundException("User not found with username "+username));

        return UserDetailsImpl.build(user);
    }
}

