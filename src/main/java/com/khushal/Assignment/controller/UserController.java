package com.khushal.Assignment.controller;

import com.khushal.Assignment.constants.APIs;
import com.khushal.Assignment.models.JWTResponse;
import com.khushal.Assignment.models.User;
import com.khushal.Assignment.security.DetailsService;
import com.khushal.Assignment.security.JwtUtil;
import com.khushal.Assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIs.USER_API)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService service;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    DetailsService detailsService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping
    public List<String> getUser() {
        LOGGER.info("Getting Users");
        return service.getAllUsers();
    }

    @PostMapping("/register")
    public Long registerUser(@RequestBody User user) {
        LOGGER.info(String.format("Registering user %s", user.getUsername()));
        user.setPassword(encoder.encode(user.getPassword()));
        return service.saveUser(user);
    }

    @GetMapping("/sq/{a}")
    public int work(@PathVariable int a) {
        LOGGER.info("Sq a");
        return a*a;
    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody User user) throws BadCredentialsException {
        LOGGER.info("Authenticating user by username: {}",user.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),null));
        UserDetails userDetails = detailsService.loadUserByUsername(user.getUsername());
        return new JWTResponse(jwtUtil.generateToken(userDetails));
    }
}