package com.vitanum.userservice.service;

import com.vitanum.userservice.data.UserEntity;
import com.vitanum.userservice.mapper.utils.ObjectMapperUtils;
import com.vitanum.userservice.repository.UserRepository;
import com.vitanum.userservice.shared.UserDto;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        // Add random ID
        userDetails.setUserId(UUID.randomUUID().toString());
        // Encrypt the user password
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        // Create the mapper
        ModelMapper modelMapper = ObjectMapperUtils.createModelMapper(MatchingStrategies.STRICT);

        // Save the user in the database
        UserEntity userEntity = saveUser(userDetails, modelMapper);

        // Map the UserEntity into a DAO
        return modelMapper.map(userEntity, UserDto.class);
    }

    @NotNull
    private UserEntity saveUser(UserDto userDetails, ModelMapper modelMapper) {
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userRepository.save(userEntity);

        return userEntity;
    }

}
