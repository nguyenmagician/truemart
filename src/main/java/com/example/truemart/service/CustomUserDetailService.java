package com.example.truemart.service;

import com.example.truemart.entity.UserTrueMart;
import com.example.truemart.repository.UserTrueMartRepository;
import com.example.truemart.tools.VocVachTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserTrueMartRepository userTrueMartRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserTrueMart user;

        Optional<UserTrueMart> u = userTrueMartRepository.findUserTrueMartByEmailEquals(s);
        if(u.isPresent()) {

            user = u.get();
            System.out.println("Trong userdettail : "+user.getPassword());
        }else {
            throw new UsernameNotFoundException("User "+ s + " not found");
        }

        UserDetails userDetails = (UserDetails) new User(user.getEmail(),user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("USER")));

        return  userDetails;
    }
}
