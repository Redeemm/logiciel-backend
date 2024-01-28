package com.reddev.logicielbackend.service;


import com.reddev.logicielbackend.auth.Requests;
import com.reddev.logicielbackend.exception.HttpHandler.NotfoundException;
import com.reddev.logicielbackend.exception.HttpHandler.Response;
import com.reddev.logicielbackend.exception.HttpHandler.UnauthorizedException;
import com.reddev.logicielbackend.repository.StudentCourseRepository;
import com.reddev.logicielbackend.model.user.User;
import com.reddev.logicielbackend.model.user.UserType;
import com.reddev.logicielbackend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;
    private final StudentCourseRepository studentCourseRepository;

    public Response activateUser(UUID id) {
        User user = userRepository.findUserById(id);

        user.setLocked(false);
        userRepository.save(user);

        return new Response(
                HttpStatus.OK,
                "Student activated Successfully: " + user.getFirstName() + " " + user.getLastName(),
                LocalDateTime.now());
    }

    public Response deactivateUser(UUID id) {
        User user = userRepository.findUserById(id);

        user.setLocked(true);
        userRepository.save(user);

        return new Response(
                HttpStatus.OK,
                "Student Deactivated Successfully: " + user.getFirstName() + " " + user.getLastName(),
                LocalDateTime.now());
    }

    public List<User> getUsers(String name) {
        if(name.equals(".")) {
            return userRepository.findByRole();
        }
        return userRepository.findByName(name);
    }

    public Object getUser(UUID id) {
        User user = userRepository.findById(id).get();
        Map<String, Object> result = new HashMap<>();

        if (user.getRole().equals(UserType.STUDENT)) {
            Object registerCourse = studentCourseRepository.findByUser(user);
            result.put("studentInfo", user);
            result.put("registerCourse", registerCourse);
            return ResponseEntity.ok().body(result);
        }
        else {
            result.put("adminInfo", user);
            return ResponseEntity.ok().body(result);
        }
    }



    public Response updateProfile(Requests request, Claims check) throws NotfoundException {

        User user = userRepository.findByEmail(check.getSubject())
                .orElseThrow(() -> new NotfoundException("User Not Found!"));


        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());


        userRepository.save(user);

        return new Response(HttpStatus.ACCEPTED,
                "User Details Edited successfully!!",
                LocalDateTime.now());
    }


    public void checkUserRole(String userEmail) throws NotfoundException, UnauthorizedException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new NotfoundException("User not found with email: " + userEmail));

        if (user.getRole() != UserType.ADMIN) {
            throw new UnauthorizedException("You are not authorized as a Student.");
        }
    }

    public void checkStudentRole(String userEmail) throws NotfoundException, UnauthorizedException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new NotfoundException("User not found with email: " + userEmail));

        if (user.getRole() != UserType.STUDENT) {
            throw new UnauthorizedException("Only Students can register courses!.");
        }
    }

    public void checkUser(String userEmail) throws NotfoundException {
        userRepository.findByEmail(userEmail).orElseThrow(() ->
                new NotfoundException("User not found with email: " + userEmail));
    }

}
