package com.danilo.sellora.infrastructure.persistence;

import com.danilo.sellora.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
