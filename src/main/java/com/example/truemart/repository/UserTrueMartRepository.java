package com.example.truemart.repository;

import com.example.truemart.entity.UserTrueMart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserTrueMartRepository extends JpaRepository<UserTrueMart,Long> {
    Optional<UserTrueMart> findUserTrueMartByEmailEquals(String email);

    @Query("select c.first_name from UserTrueMart c where c.email=:email")
    Optional<String> findFirstNameOfEmail(@Param("email")String email);
}
