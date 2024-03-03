package com.mango.customer.application.user;

import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserService implements CreateUserUseCase {

  private UserRepository userRepository;

  @Override
  public User createUser(User user) {
      return userRepository.save(user);
    }
  }

