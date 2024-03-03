package com.mango.customer.infrastructure.rest.user;

import lombok.Data;

@Data
public class CreateUserDto {

  private String name;

  private String lastName;

  private String city;

  private String address;

  private String emailAddress;

}
