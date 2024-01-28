package com.reddev.logicielbackend.model.user;

public enum UserType {
    STUDENT("STUDENT"),
    ADMIN("ADMIN");

    final String type;
    UserType (String type) {this.type = type;}
}
