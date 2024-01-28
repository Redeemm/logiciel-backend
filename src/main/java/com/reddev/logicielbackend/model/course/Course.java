package com.reddev.logicielbackend.model.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
        name = "course"
)
public class Course {
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

}
