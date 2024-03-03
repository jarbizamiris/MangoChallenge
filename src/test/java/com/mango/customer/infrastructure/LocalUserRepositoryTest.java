package com.mango.customer.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mango.customer.domain.User;
import com.mango.customer.domain.exceptions.ErrorCode;
import com.mango.customer.infrastructure.dataBase.DBUserMapper;
import com.mango.customer.infrastructure.dataBase.LocalUserRepository;
import com.mango.customer.infrastructure.exceptions.InfrastructureException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocalUserRepositoryTest {

  private LocalUserRepository localUserRepository;

  // Se obtiene la instancia verdadera.
  private DBUserMapper dbUserMapper = Mappers.getMapper( DBUserMapper.class );;

  @BeforeEach
  public void resetRepository() {
    localUserRepository = new LocalUserRepository(dbUserMapper);
  }

  @Test
  public void shouldSaveUser() {
    // GIVEN
    String userName = "testUser";
    String userEmail = "test@example.com";

    User userToSave = new User();
    userToSave.setName(userName);
    userToSave.setEmailAddress(userEmail);

    // WHEN
    User userSaved = localUserRepository.save(userToSave);

    // THEN
    assertNotNull(userSaved);
    assertEquals(userName, userSaved.getName());
    assertEquals(userEmail, userSaved.getEmailAddress());
    assertNotNull(userSaved.getId());

    Optional<User> userFromDb = localUserRepository.getUserById(userSaved.getId());

    assertTrue(userFromDb.isPresent());
    assertEquals(userSaved, userFromDb.get());
  }

  @Test
  public void shouldUpdateUser_ExistingUser() {
    // GIVEN
    User user = new User();
    user.setName("existingUser");
    user.setEmailAddress("existing@example.com");
    
    user = localUserRepository.save(user);

    String updatedName = "updatedUser";
    String updatedEmail = "updated@example.com";

    user.setName(updatedName);
    user.setEmailAddress(updatedEmail);

    // WHEN
    localUserRepository.update(user);

    // THEN
    Optional<User> userFromDatabaseOpt = localUserRepository.getUserById(user.getId());

    // Verifica si el user existe
    assertTrue(userFromDatabaseOpt.isPresent());

    User userFromDatabase = userFromDatabaseOpt.get();

    // Verificar si el usuario actualizado tiene el nombre correcto
    assertEquals(updatedName, userFromDatabase.getName());
    // Verificar si el usuario actualizado tiene el correo electrónico correcto
    assertEquals(updatedEmail, userFromDatabase.getEmailAddress());
  }

  @Test
  public void shouldUpdateUser_NonExistingUser() {

    User nonExistingUser = new User();
    nonExistingUser.setId(999L);
    nonExistingUser.setName("nonExistingUser");

    InfrastructureException exception = assertThrows(InfrastructureException.class,
      () -> localUserRepository.update(nonExistingUser)
    );
    assertEquals("Customer to update not found", exception.getMessage());
    assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());

  }

  @Test
  public void shouldGetUserById_WhenUserExists() {
    // GIVEN
    User existingUser = new User();
    existingUser.setName("existingUser");
    existingUser.setEmailAddress("existing@example.com");
    
    existingUser = localUserRepository.save(existingUser);

    // WHEN
    Optional<User> userFromDb = localUserRepository.getUserById(existingUser.getId());

    // THEN
    // Verifica si el Optional contiene un user
    assertTrue(userFromDb.isPresent());
    // Verifica si el user devuelto es el esperado
    assertEquals(existingUser, userFromDb.get());
  }

  @Test
  public void shouldGetUserById_WhenUserDoesNotExist() {
    // GIVEN

    // WHEN
    Optional<User> userFromDb = localUserRepository.getUserById(1L);

    // THEN
    assertFalse(userFromDb.isPresent());

  }

}
