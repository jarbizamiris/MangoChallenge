package com.mango.customer.infrastructure.dataBase;

import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import com.mango.customer.domain.exceptions.ErrorCode;
import com.mango.customer.infrastructure.exceptions.InfrastructureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocalUserRepository implements UserRepository {

  @Autowired
  private DBUserMapper dbUserMapper;

  private Long nextId = 1L;

  private Map<Long, UserEntity> users = new HashMap<>();

  public LocalUserRepository(DBUserMapper dbUserMapper) {
    this.dbUserMapper = dbUserMapper;
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = dbUserMapper.toDto(user);
    userEntity.setId(nextId++);
    users.put(userEntity.getId(), userEntity);

    return dbUserMapper.fromDto(userEntity);
  }

  @Override
  public void update(User user) {
    if(users.get(user.getId()) == null) {
      throw new InfrastructureException("Customer to update not found", ErrorCode.USER_NOT_FOUND);
    }
    UserEntity userEntity = dbUserMapper.toDto(user);
    users.put(user.getId(), userEntity);
  }

  @Override
  public Optional<User> getUserById(Long id) {
    UserEntity userEntity = users.get(id);
    if(Objects.isNull(userEntity)) {
      return Optional.empty();
    }
    return Optional.of(dbUserMapper.fromDto(userEntity));
  }
}
