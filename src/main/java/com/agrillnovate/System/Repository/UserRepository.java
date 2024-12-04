package com.agrillnovate.System.Repository;

import com.agrillnovate.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByEmailOrPhone(String email, String phone);
    User findUserByEmail(String email);
    boolean existsByPhone(String phone);
    List<User> findByRole(String role);
}