package com.reddev.logicielbackend.controller;

import com.reddev.logicielbackend.auth.Requests;
import com.reddev.logicielbackend.config.JwtService;
import com.reddev.logicielbackend.model.course.Course;
import com.reddev.logicielbackend.service.CourseService;
import com.reddev.logicielbackend.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final UserService userService;

    @GetMapping
    public List<Course> getCourses(@RequestParam(defaultValue = ".") String name, @RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUser(check.getSubject());

        return courseService.getCourses();
    }

    @PostMapping("create")
    public ResponseEntity<?> createCourse( @RequestHeader(value = "authorization", defaultValue = "") String auth, @RequestBody Requests request) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUserRole(check.getSubject());

        return ResponseEntity.ok(courseService.createCourse(request));
    }


    @PostMapping("register")
    public ResponseEntity<?> registerCourse(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @RequestParam UUID id) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkStudentRole(check.getSubject());

        return ResponseEntity.ok(courseService.registerCourse(check, id));
    }


    @PatchMapping("edit")
    public ResponseEntity<?> editCourse(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @RequestBody Requests requests,
            @RequestParam UUID id) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUserRole(check.getSubject());

        return ResponseEntity.ok(courseService.edithCourse(requests, id));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUser(
            @RequestHeader(value = "authorization", defaultValue = "") String auth,
            @RequestParam UUID id) throws Exception {
        Claims check = jwtService.verify(auth);
        userService.checkUserRole(check.getSubject());

        return ResponseEntity.ok(courseService.deleteProducts(id));
    }
}
