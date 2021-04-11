package com.khushal.Assignment;

import com.khushal.Assignment.models.Properties;
import com.khushal.Assignment.models.Role;
import com.khushal.Assignment.models.User;
import com.khushal.Assignment.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@SpringBootApplication
public class AssignmentApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Autowired
    UserRepo repo;
    @Autowired
    Properties properties;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        User admin = new User(null, properties.getUsername(), encoder.encode(properties.getPassword()), Role.ADMIN);
        User user = repo.findByUsername(properties.getUsername());
        if (Objects.nonNull(user)) {
            user.setPassword(encoder.encode(properties.getPassword()));
            repo.save(user);
        }else repo.save(admin);
    }
}
