package com.vitanum.userservice.controller;

import com.vitanum.userservice.mapper.utils.ObjectMapperUtils;
import com.vitanum.userservice.model.CreateUserRequest;
import com.vitanum.userservice.model.CreateUserResponse;
import com.vitanum.userservice.service.UsersService;
import com.vitanum.userservice.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersService usersService;

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest userDetails) {
        ModelMapper modelMapper = ObjectMapperUtils.createModelMapper(MatchingStrategies.STRICT);
        CreateUserResponse response = createUser(userDetails, modelMapper);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private CreateUserResponse createUser(@RequestBody CreateUserRequest userDetails, ModelMapper modelMapper) {
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = usersService.createUser(userDto);

        return modelMapper.map(createdUser, CreateUserResponse.class);
    }
}
