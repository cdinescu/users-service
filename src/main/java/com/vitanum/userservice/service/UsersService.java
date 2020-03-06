package com.vitanum.userservice.service;

import com.vitanum.userservice.shared.UserDto;

public interface UsersService {
    UserDto createUser(UserDto userDetails);
}
