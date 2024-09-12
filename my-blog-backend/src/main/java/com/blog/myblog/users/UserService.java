package com.blog.myblog.users;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blog.myblog.common.entity.UserEntity;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }
}
