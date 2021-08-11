package com.tweets.service;

import com.tweets.entities.User;
import com.tweets.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        if (user.getId() == null) {
            log.info("User does not exists");
            log.info("Saving user...");
            return userRepository.save(user);
        } else {
            Optional<User> userFound = userRepository.findById(user.getId());
            return userFound.get();
        }
    }

}
