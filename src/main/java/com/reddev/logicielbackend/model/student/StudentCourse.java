package com.reddev.logicielbackend.model.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reddev.logicielbackend.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(
        name = "student_course"
)
public class StudentCourse {
    @Id
    @GeneratedValue
    private UUID id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
    private String courseCode;
    private String courseName;
    private String lecturerName;
    private String lectureRoom;

    @JsonIgnore
    @ManyToOne
    private User user;

}
