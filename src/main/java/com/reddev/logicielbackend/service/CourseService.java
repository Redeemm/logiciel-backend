package com.reddev.logicielbackend.service;

import com.reddev.logicielbackend.auth.Requests;
import com.reddev.logicielbackend.exception.HttpHandler.NotAcceptableException;
import com.reddev.logicielbackend.exception.HttpHandler.NotfoundException;
import com.reddev.logicielbackend.exception.HttpHandler.Response;
import com.reddev.logicielbackend.model.course.Course;
import com.reddev.logicielbackend.model.student.StudentCourse;
import com.reddev.logicielbackend.repository.StudentCourseRepository;
import com.reddev.logicielbackend.model.user.User;
import com.reddev.logicielbackend.repository.CourseRepository;
import com.reddev.logicielbackend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final StudentCourseRepository studentCourseRepository;

    public List<Course> getCourses () {
        return courseRepository.findAll();
    }

    public Response createCourse(Requests request) throws NotAcceptableException {

        Optional<Course> course = courseRepository.findByCourseCodeAndCourseName(request.getCourseCode(), request.getCourseName());

        if (course.isPresent()) {
            throw new NotAcceptableException("You have already created:=> " + request.getCourseName());
        }

        var create = Course.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .courseCode(request.getCourseCode())
                .courseName(request.getCourseName())
                .lecturerName(request.getLecturerName())
                .lectureRoom(request.getLectureRoom())
                .build();

        courseRepository.save(create);

        return new Response(HttpStatus.CREATED,
                "Course Created successfully!!",
                LocalDateTime.now());
    }


    public Response registerCourse(Claims check, UUID id) throws NotfoundException, NotAcceptableException {

        Course course = courseRepository.findById(id).orElseThrow(() -> new NotfoundException("Course not found!"));
        User user = userRepository.findByEmail(check.getSubject()).orElseThrow(() -> new NotfoundException("User not found!"));

        Optional<StudentCourse> s = studentCourseRepository.findByUserAndCourseCode(user, course.getCourseCode());

        if (s.isPresent()) {
            throw new NotAcceptableException("You have already registered:=> " + course.getCourseName());
        }

        var register = StudentCourse.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .lecturerName(course.getLecturerName())
                .lectureRoom(course.getLectureRoom())
                .user(user)
                .build();

        studentCourseRepository.save(register);

        return new Response(HttpStatus.CREATED,
                user.getFirstName() + " " + user.getLastName() + " successfully registered:=> " + course.getCourseName(),
                LocalDateTime.now());
    }


    public Response edithCourse(Requests request, UUID id) throws NotfoundException {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Course Not Found!"));

        course.setUpdatedAt(LocalDateTime.now());
        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setLecturerName(request.getLecturerName());
        course.setLectureRoom(request.getLectureRoom());

        courseRepository.save(course);

        return new Response(HttpStatus.ACCEPTED,
                "Course Edited successfully!!",
                LocalDateTime.now());
    }

    public Response deleteProducts (UUID id) throws NotfoundException {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotfoundException("Course do not exist"));

        courseRepository.deleteById(course.getId());

        return new Response(HttpStatus.OK,
                "Course Deleted successfully!!",
                LocalDateTime.now());
    }
}
