package com.mango.customer.application.slogan;

import com.mango.customer.application.exceptions.ApplicationValidationException;
import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import com.mango.customer.domain.exceptions.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSloganService implements CreateSloganUseCase {

  private UserRepository userRepository;

  @Override
  public void createSlogan(Long userId, String slogan) {
    User user = userRepository.getUserById(userId)
        .orElseThrow(()-> new ApplicationValidationException("User: " + userId + " not found", ErrorCode.USER_NOT_FOUND));

    user.addSlogan(slogan);

    userRepository.update(user);

  }
}
