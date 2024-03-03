package com.mango.customer.infrastructure.rest.user;

import com.mango.customer.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestUserMapper {

  User fromDto (CreateUserDto createUserDto);

  UserDto toDto (User user);

  User fromDto (UpdateUserDto updateUserDto);

}
