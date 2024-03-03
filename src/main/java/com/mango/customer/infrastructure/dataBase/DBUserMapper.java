package com.mango.customer.infrastructure.dataBase;

import com.mango.customer.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DBUserMapper {
  //@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

  UserEntity toDto(User user);

  User fromDto(UserEntity userEntity);

}
