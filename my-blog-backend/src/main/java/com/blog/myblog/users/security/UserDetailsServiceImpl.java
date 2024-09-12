package com.blog.myblog.users.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.myblog.common.entity.UserEntity;
import com.blog.myblog.users.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getMail(), user.getPassword(), new ArrayList<>());
    }
}
