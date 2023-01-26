package com.saldmy.conferencemanagementrest.security;

import com.saldmy.conferencemanagementrest.entity.User;
import com.saldmy.conferencemanagementrest.exception.UserNotFoundException;
import com.saldmy.conferencemanagementrest.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.find(email).orElseThrow(() -> new UserNotFoundException(email));

        return new UserDetailsImpl(user);
    }

}
