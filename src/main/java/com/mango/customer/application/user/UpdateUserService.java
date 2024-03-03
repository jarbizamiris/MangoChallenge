package com.mango.customer.application.user;

import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

  private UserRepository userRepository;

  @Override
  public void updateUser(User user) {
    userRepository.update(user);
  }
}
