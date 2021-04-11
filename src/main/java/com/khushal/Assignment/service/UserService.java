package com.khushal.Assignment.service;

import com.khushal.Assignment.models.User;
import com.khushal.Assignment.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepo repo;

    public Long saveUser(User user) {
        LOGGER.debug(String.format("Saving User %s to database", user.getUsername()));
        return repo.save(user).getId();
    }

    public List<String> getAllUsers() {
        LOGGER.debug("Getting all User from database");
        List<String> usernames = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(s -> usernames.add(s.getUsername()));
        return usernames;
    }

}