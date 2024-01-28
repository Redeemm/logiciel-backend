---
Here is a link to the API Documentation on Postman: `https://documenter.getpostman.com/view/22112281/2s9YyqihLa `

# API Documentation

## Table of Contents
1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
3. [API Endpoints](#api-endpoints)
    - [Add New Student Information](#add-new-student-information)
    - [Add New Course Information](#add-new-course-information)
    - [Fetch All Student Information](#fetch-all-student-information)
    - [Fetch Single Student Data by ID and Associated Courses](#fetch-single-student-data-by-id-and-all-associated-courses)
    - [Fetch All Course Information](#fetch-all-course-information)
    - [Fetch Single Course Information by ID](#fetch-single-course-information-by-id)

## Introduction
This document provides details on the RESTful APIs implemented in our system to manage student and course information. Please follow the guidelines below to interact with the APIs.

## Getting Started
To use the APIs, make sure you have the following prerequisites:
- [List any prerequisites, such as authentication tokens, API keys, etc.]


You can access the APIs at `http://localhost:8080`.

## API Endpoints

### Registering New User (Student/Admin)
**Endpoint:** `POST /api/v1/auth/register`

**Request Body:**
```json
{
    "firstName" : "Redeemer",
    "lastName" : "Dela",
    "phoneNumber" : "+233547915491",
    "email" : "admin2@logic.com",
    "password": "adminuser123",
    "role" : "ADMIN" // STUDENT Or ADMIN
}
```

**Response:**
```json
{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAbG9naWMuY29tIiwiaWF0IjoxNzA2NDA0MDQ2LCJleHAiOjE3MDY0MDQ5NDZ9.WmKEqsYSP-H0Q7m6z0uKdhK3Hv9waI-HrwiLxAupv_g",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAbG9naWMuY29tIiwiaWF0IjoxNzA2NDA0MDQ2LCJleHAiOjE3OTI4MDQwNDZ9.K_FINTBotGcs0WgPqvoJ66Dq0wNu6Au9PD2lf9Y_hQc"
}
```

### Login User (Student/Admin)
**Endpoint:** `POST /api/v1/auth/login`

**Request Body:**
```json
{
    "email" : "admin@logic.com", // student1@logic.com
    "password" : "adminuser123"
}
```

**Response:**
```json
{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAbG9naWMuY29tIiwiaWF0IjoxNzA2NDA0MDQ2LCJleHAiOjE3MDY0MDQ5NDZ9.WmKEqsYSP-H0Q7m6z0uKdhK3Hv9waI-HrwiLxAupv_g",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAbG9naWMuY29tIiwiaWF0IjoxNzA2NDA0MDQ2LCJleHAiOjE3OTI4MDQwNDZ9.K_FINTBotGcs0WgPqvoJ66Dq0wNu6Au9PD2lf9Y_hQc"
}
```

### Get User Information
**Endpoint:** `GET /api/v1/user/info`


**Response:**
```json
{
    "studentInfo": {
        "id": "f04cbb66-0d08-4b61-b052-bd9c47fc2887",
        "createdAt": "2024-01-28T00:48:47.276468",
        "email": "student1@logic.com",
        "firstName": "Edith",
        "lastName": "Agbonuglah",
        "phoneNumber": "+233547915222",
        "role": "STUDENT",
        "locked": false,
        "enabled": true,
        "authorities": [
            {
                "authority": "STUDENT"
            }
        ],
        "accountNonLocked": true,
        "username": "student1@logic.com",
        "accountNonExpired": true,
        "credentialsNonExpired": true
    },
    "registerCourse": [
        {
            "id": "9c6fcdd2-ff95-4157-8ed5-97b9c1abe556",
            "createdAt": "2024-01-28T00:59:13.454621",
            "courseCode": "DCIT400",
            "courseName": "Project Work",
            "lecturerName": "Dr. Ferdinard Katsikpo",
            "lectureRoom": "JQB Room 19"
        },
        {
            "id": "800a3a13-accf-44cb-80c7-670e2f1bcd25",
            "createdAt": "2024-01-28T00:59:53.604473",
            "courseCode": "DCIT404",
            "courseName": "Advanced Database",
            "lecturerName": "Dr. Ferdinard Katsikpo",
            "lectureRoom": "JQB Room 19"
        }
    ]
}
```

### Add New Course Information
**Endpoint:** `POST /api/courses`

**Request Body:**
```json
{
  "courseCode": "C101",
  "courseName": "Introduction to Programming",
  "lecturerName": "Dr. Smith",
  "lectureRoom": "Room 203"
}
```

**Response:**
```json
{
  "courseCode": "C101",
  "courseName": "Introduction to Programming",
  "lecturerName": "Dr. Smith",
  "lectureRoom": "Room 203"
}
```

### Fetch All Student Information
**Endpoint:** `GET /api/students`

**Response:**
```json
[
  {
    "id": "123",
    "name": "John Doe",
    "age": 20,
    "courses": ["C101", "C102"]
  },
  // ... other students
]
```

### Fetch Single Student Data by ID and Associated Courses
**Endpoint:** `GET /api/students/:id`

**Response:**
```json
{
  "id": "123",
  "name": "John Doe",
  "age": 20,
  "courses": [
    {
      "courseCode": "C101",
      "courseName": "Introduction to Programming",
      "lecturerName": "Dr. Smith",
      "lectureRoom": "Room 203"
    },
    // ... other courses
  ]
}
```

### Fetch All Course Information
**Endpoint:** `GET /api/courses`

**Response:**
```json
[
  {
    "courseCode": "C101",
    "courseName": "Introduction to Programming",
    "lecturerName": "Dr. Smith",
    "lectureRoom": "Room 203"
  },
  // ... other courses
]
```

### Fetch Single Course Information by ID
**Endpoint:** `GET /api/courses/:courseCode`

**Response:**
```json
{
  "courseCode": "C101",
  "courseName": "Introduction to Programming",
  "lecturerName": "Dr. Smith",
  "lectureRoom": "Room 203"
}
```

Feel free to customize this template based on your API specifications and use a documentation tool like Swagger, API Blueprint, or Markdown to present it in your README file.
