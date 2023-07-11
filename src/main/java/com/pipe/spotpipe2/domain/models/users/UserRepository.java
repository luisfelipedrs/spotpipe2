package com.pipe.spotpipe2.domain.models.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
