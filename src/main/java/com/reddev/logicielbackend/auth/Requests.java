package com.reddev.logicielbackend.auth;


import com.reddev.logicielbackend.model.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Requests {

  private UserType role;
  private String courseName;
  private String courseCode;
  private String lectureRoom;
  private String lecturerName;
  private String password;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;




}
