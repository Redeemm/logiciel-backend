package com.reddev.logicielbackend.controller;

import com.reddev.logicielbackend.config.JwtService;
import com.reddev.logicielbackend.model.user.User;
import com.reddev.logicielbackend.repository.UserRepository;
import com.reddev.logicielbackend.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping
    public List<User> getUsers(@RequestParam(defaultValue = ".") String name, @RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUserRole(check.getSubject());

        return userService.getUsers(name);
    }


    @GetMapping("get")
    public Object getUser(@RequestHeader(value = "authorization", defaultValue = "") String auth, @RequestParam UUID id) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUser(check.getSubject());

        return userService.getUser(id);
    }

    @PatchMapping("deactivate")
    public ResponseEntity<?> deactivateUser(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @RequestParam UUID id) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUserRole(check.getSubject());

        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    @PatchMapping("activate")
    public ResponseEntity<?> activateUser(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @RequestParam UUID id) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUserRole(check.getSubject());

        return ResponseEntity.ok(userService.activateUser(id));
    }
}
