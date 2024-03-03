package com.mango.customer.infrastructure.rest.user;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {

  private long id;

  private String name;

  private String lastName;

  private String address;

  private String city;

  private String emailAddress;

  private List<String> slogans;

}
