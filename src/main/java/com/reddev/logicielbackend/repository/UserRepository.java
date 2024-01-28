package com.reddev.logicielbackend.repository;


import com.reddev.logicielbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.role = 'STUDENT' ")
    List<User> findByRole();

    @Query("select u from User u where u.role = 'STUDENT' " +
            "AND (u.firstName like ?1% or u.lastName like ?1%)")
    List<User> findByName(String name);

    @Query("select u from User u where u.id = ?1")
    User findUserById(UUID id);
}

