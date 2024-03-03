package com.mango.customer.infrastructure;

import com.mango.customer.application.slogan.CreateSloganService;
import com.mango.customer.application.slogan.CreateSloganUseCase;
import com.mango.customer.application.user.CreateUserService;
import com.mango.customer.application.user.CreateUserUseCase;
import com.mango.customer.application.user.GetUserService;
import com.mango.customer.application.user.GetUserUseCase;
import com.mango.customer.application.user.UpdateUserService;
import com.mango.customer.application.user.UpdateUserUseCase;
import com.mango.customer.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  CreateUserUseCase createUserUseCase (UserRepository userRepository) {
    return new CreateUserService(userRepository);
  }

  @Bean
  UpdateUserUseCase updateUserUseCase (UserRepository userRepository) {
    return new UpdateUserService(userRepository);
  }

  @Bean
  CreateSloganUseCase createSloganUseCase (UserRepository userRepository) {
    return new CreateSloganService(userRepository);
  }

  @Bean
  GetUserUseCase getUserUseCase (UserRepository userRepository) {
    return new GetUserService(userRepository);
  }

}
