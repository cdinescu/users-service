package com.vitanum.userservice.controller;

import com.vitanum.userservice.model.CreateUserRequest;
import com.vitanum.userservice.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
//@WebFluxTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void createUserAllFieldsOK() {
        CreateUserRequest request = buildUserRequest("Obi-Wan", "Kenobi", "pass", "test@email.com");

        postAndVerifyDiary(request, HttpStatus.CREATED);
    }

    private CreateUserRequest buildUserRequest(String firstName, String lastName, String password, String email) {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setPassword(password);
        request.setEmail(email);

        return request;
    }

    private void postAndVerifyDiary(CreateUserRequest request, HttpStatus expectedStatus) {
/*        client.post()
                .uri("/users")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);*/
    }

}
