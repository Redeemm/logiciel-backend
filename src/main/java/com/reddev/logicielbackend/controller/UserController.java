package com.reddev.logicielbackend.controller;


import com.reddev.logicielbackend.auth.Requests;
import com.reddev.logicielbackend.config.JwtService;
import com.reddev.logicielbackend.exception.HttpHandler.NotAcceptableException;
import com.reddev.logicielbackend.repository.StudentCourseRepository;
import com.reddev.logicielbackend.model.user.User;
import com.reddev.logicielbackend.model.user.UserType;
import com.reddev.logicielbackend.repository.UserRepository;
import com.reddev.logicielbackend.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping( "api/v1/user")
@RequiredArgsConstructor

public class UserController {

    public final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final StudentCourseRepository studentCourseRepository;

    @GetMapping("info")
    private ResponseEntity<?> userInfo(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
        Claims check = jwtService.verify(auth);
        User user = userRepository.findByEmail(check.getSubject())
                .orElseThrow(() -> new NotAcceptableException("Get out of here. HACKER!!"));
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


    @PatchMapping("update")
    public ResponseEntity<?> updateProfile(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @RequestBody Requests requests) throws Exception {
        Claims check = jwtService.verify(auth);

        return ResponseEntity.ok(userService.updateProfile(requests, check));
    }

}
