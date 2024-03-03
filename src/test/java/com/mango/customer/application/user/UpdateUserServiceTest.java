package com.mango.customer.application.user;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UpdateUserService updateUserService;

  @Test
  public void shouldUpdateUser() {
    User updatedUser = new User();
    updatedUser.setId(1L);
    updatedUser.setName("updatedUser");

    updateUserService.updateUser(updatedUser);

    verify(userRepository, times(1)).update(updatedUser);

  }

}
