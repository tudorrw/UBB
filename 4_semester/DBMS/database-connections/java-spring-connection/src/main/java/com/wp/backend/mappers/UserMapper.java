package com.wp.backend.mappers;

import com.wp.backend.dtos.SignUpDto;
import com.wp.backend.dtos.UserDto;
import com.wp.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto userDto);
}
