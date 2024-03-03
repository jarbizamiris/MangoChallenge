package com.mango.customer.infrastructure.dataBase;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class UserEntity {

  private Long id;

  private String name;

  private String lastName;

  private String address;

  private String city;

  private String emailAddress;

  private List<String> slogans = new ArrayList<>();

}
