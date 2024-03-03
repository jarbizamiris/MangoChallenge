package com.mango.customer.application.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mango.customer.application.exceptions.ApplicationNotFoundException;
import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import com.mango.customer.domain.exceptions.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private GetUserService getUserService;

  @Test
  public void shouldGetUser_ExistingUser() {
    Long userId = 1L;
    String name = "existingUser";
    User existingUser = new User();
    existingUser.setId(userId);
    existingUser.setName(name);

    when(userRepository.getUserById(userId)).thenReturn(Optional.of(existingUser));

    User retrievedUser = getUserService.getUser(userId);

    assertNotNull(retrievedUser);
    assertEquals(userId, retrievedUser.getId());
    assertEquals(name, retrievedUser.getName());
  }

  @Test
  public void shouldGetUser_NonExistingUser() {
    Long nonExistingUserId = 999L;

    when(userRepository.getUserById(nonExistingUserId)).thenReturn(Optional.empty());

    ApplicationNotFoundException ex = assertThrows(ApplicationNotFoundException.class, () -> getUserService.getUser(nonExistingUserId));

    assertEquals("User: " + nonExistingUserId + " not found", ex.getMessage());
    assertEquals(ErrorCode.USER_NOT_FOUND, ex.getErrorCode());

  }

}
