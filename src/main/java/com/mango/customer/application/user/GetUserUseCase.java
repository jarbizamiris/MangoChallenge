package com.mango.customer.application.user;

import com.mango.customer.domain.User;

public interface GetUserUseCase {
  User getUser(Long id);

}
