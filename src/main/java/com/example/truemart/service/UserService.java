package com.example.truemart.service;

import com.example.truemart.DTO.RegisterDTO;
import com.example.truemart.entity.Role;
import com.example.truemart.entity.UserTrueMart;
import com.example.truemart.repository.RoleRepository;
import com.example.truemart.repository.UserTrueMartRepository;
import com.example.truemart.tools.ConvertTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder pw;

    private final UserTrueMartRepository userTrueMartRepository;
    private final RoleRepository roleRepository;

    public UserService(UserTrueMartRepository userTrueMartRepository, RoleRepository roleRepository) {
        this.userTrueMartRepository = userTrueMartRepository;
        this.roleRepository = roleRepository;
    }

    public boolean checkEmail(String email) {
        return userTrueMartRepository.findUserTrueMartByEmailEquals(email).isEmpty() ? true : false;
    }

    public void saveUserWithUserRole(RegisterDTO registerDTO) {
        // Convert tu RegisterDTO -> UserTrueMart
        UserTrueMart user = convertRegisterDTOToUserTrueMart(registerDTO);
        user.addRole(getRoleUser());
        // Save vao database
        userTrueMartRepository.save(user);
    }

    public Role getRoleUser() {
        return roleRepository.getOne(Long.valueOf(2));
    }

    public Role getRoleAdmin() {
        return roleRepository.getOne(Long.valueOf(1));
    }

    public String getFirstNameOfUserHaveEmail(String email) {
        return userTrueMartRepository.findFirstNameOfEmail(email).orElse("");
    }

    public UserTrueMart convertRegisterDTOToUserTrueMart(RegisterDTO r) {
        System.out.println(r.toString());
        UserTrueMart u= new UserTrueMart();
        u.setFirst_name(r.getFirstname());
        u.setLast_name(r.getLastname());
        u.setEmail(r.getEmail());
        u.setTelephone(r.getTelephone());
        u.setPassword(pw.encode(r.getPassword()));
        u.setSubscribe(r.isYesSubscribe());
        // Chu y ko co add role luc nay

        return  u;
    }

    public UserTrueMart getUserByEmail(String email) {
        Optional<UserTrueMart> user = userTrueMartRepository.findUserTrueMartByEmailEquals(email);

        if(user.isPresent()) {
            return user.get();
        }

        return null;
    }

    public UserTrueMart saveUser(UserTrueMart user) {
        return userTrueMartRepository.save(user);
    }
}
