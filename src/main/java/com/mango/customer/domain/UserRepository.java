package com.mango.customer.domain;

import java.util.Optional;

public interface UserRepository {

  User save(User user);

  void update(User user);

  Optional<User> getUserById(Long id);

}
