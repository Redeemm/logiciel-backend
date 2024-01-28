package com.reddev.logicielbackend.repository;

import com.reddev.logicielbackend.model.student.StudentCourse;
import com.reddev.logicielbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, UUID> {
    Optional<StudentCourse> findByUserAndCourseCode(User user, String courseCode);

    List<StudentCourse> findByUser(User user);
}