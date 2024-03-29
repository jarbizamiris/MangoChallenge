package com.mango.customer.infrastructure.rest.user;

import com.mango.customer.application.user.CreateUserUseCase;
import com.mango.customer.application.user.GetUserUseCase;
import com.mango.customer.application.user.UpdateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

  @Autowired
  private CreateUserUseCase createUserUseCase;

  @Autowired
  private RestUserMapper userMapper;

  @Autowired
  private GetUserUseCase getUserUseCase;

  @Autowired
  private UpdateUserUseCase updateUserUseCase;

  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto createUser(@RequestBody CreateUserDto createUserDto) {
    return userMapper.toDto(createUserUseCase.createUser(userMapper.fromDto(createUserDto)));
  }

  @GetMapping("/user/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public UserDto getUser(@PathVariable(name = "userId") Long userId) {
    return userMapper.toDto(getUserUseCase.getUser(userId));
  }

  @PutMapping("/updateUser")
  @ResponseStatus(HttpStatus.OK)
  public void updateUser(@RequestBody UpdateUserDto updateUserDto) {
    updateUserUseCase.updateUser(userMapper.fromDto(updateUserDto));
  }

}
