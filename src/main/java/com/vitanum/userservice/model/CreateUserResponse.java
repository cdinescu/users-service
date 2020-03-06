package com.vitanum.userservice.model;

import lombok.Data;

@Data
public class CreateUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
