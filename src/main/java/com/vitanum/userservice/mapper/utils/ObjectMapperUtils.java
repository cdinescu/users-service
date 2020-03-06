package com.vitanum.userservice.mapper.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MatchingStrategy;

public class ObjectMapperUtils {

    private ObjectMapperUtils() {

    }

    public static ModelMapper createModelMapper(MatchingStrategy matchingStrategy) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);

        return modelMapper;
    }

}
