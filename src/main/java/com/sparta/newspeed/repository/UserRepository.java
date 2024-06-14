package com.sparta.newspeed.repository;

import com.sparta.newspeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickName);

    Optional<User> findById(Long id);
    @Modifying
    @Query("UPDATE User u SET u.authenticated = true WHERE u.email = :email")
    void updateAuthenticated(@Param("email") String email);
}

