package com.mango.customer.application.user;

import com.mango.customer.application.exceptions.ApplicationNotFoundException;
import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import com.mango.customer.domain.exceptions.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUserService implements GetUserUseCase {

  private UserRepository userRepository;

  @Override
  public User getUser(Long userId) {
    return userRepository.getUserById(userId).orElseThrow(()->
        new ApplicationNotFoundException("User: " + userId + " not found", ErrorCode.USER_NOT_FOUND));
  }
}
