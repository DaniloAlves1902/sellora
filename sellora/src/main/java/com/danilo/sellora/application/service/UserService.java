package com.danilo.sellora.application.service;

import com.danilo.sellora.domain.model.User;
import com.danilo.sellora.exceptions.UserNotFoundException;
import com.danilo.sellora.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> existsByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }

    public User update(Long id, User user) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        return userRepository.save(userToUpdate);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
