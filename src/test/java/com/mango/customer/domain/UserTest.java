package com.mango.customer.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.mango.customer.domain.exceptions.DomainValidationException;
import com.mango.customer.domain.exceptions.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTest {

  @Test
  public void addSloganBeyondLimits_shouldThrowException() {
    // GIVEN
    User user = new User();

    for (int i = 0; i < User.MAX_SLOGANS; i++) {
      user.addSlogan("Slogan " + (i + 1));
    }

    // WHEN / THEN
    DomainValidationException ex = assertThrows(DomainValidationException.class, () -> user.addSlogan("New Slogan"));

    assertEquals("You have reached the max slogan possible (" + User.MAX_SLOGANS + ")", ex.getMessage());
    assertEquals(ErrorCode.MAX_SLOGAN_REACHED, ex.getErrorCode());
  }

}
