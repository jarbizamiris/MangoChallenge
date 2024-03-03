package com.mango.customer.application.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CreateUserService createUserService;

  @Test
  public void shouldCreateUser() {
    User userToSave = new User();
    userToSave.setName("testuser");
    userToSave.setEmailAddress("test@example.com");

    when(userRepository.save(any(User.class))).thenReturn(userToSave);

    User savedUser = createUserService.createUser(userToSave);

    assertNotNull(savedUser);
    assertEquals("testuser", savedUser.getName());
    assertEquals("test@example.com", savedUser.getEmailAddress());

    verify(userRepository, times(1)).save(userToSave);
  }

}
