package com.amovsesyants.events.writer.repository;

import com.amovsesyants.events.writer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
