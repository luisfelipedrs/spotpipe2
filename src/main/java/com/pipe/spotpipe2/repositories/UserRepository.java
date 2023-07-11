package com.pipe.spotpipe2.repositories;

import com.pipe.spotpipe2.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
