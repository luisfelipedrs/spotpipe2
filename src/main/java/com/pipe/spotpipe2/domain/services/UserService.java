package com.pipe.spotpipe2.domain.services;

import com.pipe.spotpipe2.domain.models.users.UserModel;
import com.pipe.spotpipe2.domain.models.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
