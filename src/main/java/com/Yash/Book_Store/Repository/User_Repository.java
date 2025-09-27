package com.Yash.Book_Store.Repository;

import com.Yash.Book_Store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User_Repository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
}
