package com.mango.customer.domain;

import com.mango.customer.domain.exceptions.DomainValidationException;
import com.mango.customer.domain.exceptions.ErrorCode;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class User {

  public static final int MAX_SLOGANS = 3;

  private Long id;

  private String name;

  private String lastName;

  private String address;

  private String city;

  private String emailAddress;

  @Setter(AccessLevel.NONE)
  private List<String> slogans = new ArrayList<>();

  public void addSlogan(String slogan) {
    if (this.slogans.size() == MAX_SLOGANS) {
      throw new DomainValidationException("You have reached the max slogan possible (" + MAX_SLOGANS + ")",
          ErrorCode.MAX_SLOGAN_REACHED);
    }
    this.slogans.add(slogan);
  }

}
