package com.mango.customer.infrastructure.rest.user;

import lombok.Data;

@Data
public class UpdateUserDto {

  private long id;

  private String name;

  private String lastName;

  private String address;

  private String city;

  private String emailAddress;

}
