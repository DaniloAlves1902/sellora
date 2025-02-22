package com.danilo.sellora.application.service;

import com.danilo.sellora.domain.model.Order;
import com.danilo.sellora.domain.model.User;
import com.danilo.sellora.domain.model.enums.UserType;
import com.danilo.sellora.exceptions.UserNotFoundException;
import com.danilo.sellora.infrastructure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Verifica se um cliente é elegível para usar a carteira.
     * A carteira é ativada se o usuário:
     * - Fez pelo menos 10 compras acima de R$1000 cada, ou
     * - Comprou mais de R$12000 em produtos no total.
     */
    public void checkWalletEligibility(User user) {
        if (user.getUserType() == UserType.CLIENT) {
            List<Order> orders = user.getOrders();

            long ordersAbove1000 = orders.stream()
                    .filter(order -> order.getTotalPrice().compareTo(new BigDecimal("1000")) > 0)
                    .count();

            BigDecimal totalSpent = orders.stream()
                    .map(Order::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (ordersAbove1000 >= 10 || totalSpent.compareTo(new BigDecimal("12000")) > 0) {
                user.setWalletEligible(true);
                userRepository.save(user);
            }
        }
    }

    public List<User> listUserByEmail(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return userRepository.findByEmail(email);
    }

    public List<User> listUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new UserNotFoundException("There are no registered users");
        }

        return userRepository.findAll();
    }

    public User listUsersById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }

    public User createUser(User user) {
        if (user.getUserType() == UserType.ADMIN) {
            return userRepository.save(user);
        }

        if (user.getUserType() == UserType.SELLER) {
            if (user.getUserType() != UserType.CLIENT) {
                throw new SecurityException("Only admin users can create seller users");
            }
            return userRepository.save(user);
        }

        throw new SecurityException("Only ADMIN or SELLER users can create users");
    }

    public void update(Long id, User user) {
        User userToUpdate = listUsersById(id);

        if (user.getUserType() == UserType.ADMIN) {
            userToUpdate.setName(user.getName());
            userToUpdate.setDocument(user.getDocument());
            userToUpdate.setEmail(user.getEmail());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userToUpdate.setPassword(user.getPassword());
            }

        } else {
            userToUpdate.setName(user.getName());
        }

        userRepository.save(userToUpdate);
    }

    public void delete(Long id) {
        User user = listUsersById(id);
        if (user.getUserType() == UserType.ADMIN) {
            throw new SecurityException("Admin users cannot be deleted");
        }

        userRepository.deleteById(id);
    }

}