package com.mango.customer.application.slogan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mango.customer.application.exceptions.ApplicationValidationException;
import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import com.mango.customer.domain.exceptions.DomainValidationException;
import com.mango.customer.domain.exceptions.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateSloganServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CreateSloganService createSloganService;

  @Captor
  ArgumentCaptor<User> userCaptor;

  @Test
  void shouldCreateSlogan_ExistingUser() {
    // GIVEN
    Long userId = 1L;
    String slogan = "Test Slogan";

    User user = new User();
    user.setId(userId);
    user.setName("testUser");

    when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
    // WHEN
    createSloganService.createSlogan(userId, slogan);

    // THEN
    // Se valida que la actualización de la base de datos se haya llamado con el slogan agregado.
    // Se utilizará ArgumentCaptors (https://www.baeldung.com/mockito-argumentcaptor)

    verify(userRepository, times(1)).update(userCaptor.capture());
    User updatedUser = userCaptor.getValue();

    assertEquals(user, updatedUser);
    // Se valida el tamaño del array, para luego en la linea siguiente, poder hacer el get(0) de forma segura
    assertEquals(1, updatedUser.getSlogans().size());
    assertEquals(slogan, updatedUser.getSlogans().get(0));
  }

  @Test
  public void testCreateSlogan_UserNotFound() {
    // GIVEN
    Long userId = 1L;
    String slogan = "Test slogan";
    when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

    ApplicationValidationException ex = assertThrows(ApplicationValidationException.class, () -> createSloganService.createSlogan(userId, slogan));

    assertEquals("User: " + userId + " not found", ex.getMessage());
    assertEquals(ErrorCode.USER_NOT_FOUND, ex.getErrorCode());

  }
}
