package com.vitanum.userservice.controller;

import com.vitanum.userservice.model.CreateUserRequest;
import com.vitanum.userservice.model.CreateUserResponse;
import com.vitanum.userservice.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")
public class UserControllerTest {
    public static final String FIRST_NAME = "Obi-Wan";
    public static final String LAST_NAME = "Kenobi";
    public static final String PASSWORD = "pass";
    public static final String EMAIL = "test@email.com";
    public static final String INVALID_EMAIL = "@mail.com";
    public static final String OTHER_VALID_EMAIL = "test2@email.com";

    private RestTemplate restTemplate = new RestTemplate();

    private URI uri;

    @Autowired
    private UserRepository userRepository;

    public UserControllerTest() throws URISyntaxException {
        String baseUrl = "http://127.0.0.1:8080/users";
        this.uri = new URI(baseUrl);
    }

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void createUserAllFieldsOK() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, PASSWORD, EMAIL);

        // Act
        ResponseEntity<CreateUserResponse> result = restTemplate.postForEntity(uri, request, CreateUserResponse.class);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        checkResult(request, result.getBody());
    }
    @Test
    public void duplicateEmail() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, PASSWORD, "test3@gmail.com");
        ResponseEntity<CreateUserResponse> result = restTemplate.postForEntity(uri, request, CreateUserResponse.class);

        // Act & Assert
        checkBadRequest(request);
    }


    @Test
    public void userWithNullFirstName() {
        // Arrange
        CreateUserRequest request = buildUserRequest(null, LAST_NAME, PASSWORD, EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithInvalidFirstName() {
        // Arrange
        CreateUserRequest request = buildUserRequest("A", LAST_NAME, PASSWORD, OTHER_VALID_EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithNullLastName() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, null, PASSWORD, EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithInvalidLastName() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, "B", PASSWORD, OTHER_VALID_EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithNullPassword() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, null, EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithPasswordTooShort() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, "abc", OTHER_VALID_EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithPasswordTooLong() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, "abcdabcdabcdabcdabcd", OTHER_VALID_EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithNullEmail() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, PASSWORD, null);

        // Act & Assert
        checkBadRequest(request);
    }

    @Test
    public void userWithInvalidEmail() {
        // Arrange
        CreateUserRequest request = buildUserRequest(FIRST_NAME, LAST_NAME, PASSWORD, INVALID_EMAIL);

        // Act & Assert
        checkBadRequest(request);
    }

    private CreateUserRequest buildUserRequest(String firstName, String lastName, String password, String email) {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setPassword(password);
        request.setEmail(email);

        return request;
    }

    private void checkResult(CreateUserRequest request, CreateUserResponse body) {
        assertEquals(request.getFirstName(), body.getFirstName());
        assertEquals(request.getLastName(), body.getLastName());
        assertEquals(request.getEmail(), body.getEmail());

        assertNotNull(body.getUserId());
    }

    private void checkBadRequest(CreateUserRequest request) {
        try {
            ResponseEntity<CreateUserResponse> result = restTemplate.postForEntity(uri, request, CreateUserResponse.class);
        } catch (HttpClientErrorException theException) {
            //Verify bad request
            assertEquals(HttpStatus.BAD_REQUEST, theException.getStatusCode());
        }
    }

}
