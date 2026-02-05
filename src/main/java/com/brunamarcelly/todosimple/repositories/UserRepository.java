package com.brunamarcelly.todosimple.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brunamarcelly.todosimple.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
