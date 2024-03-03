package com.mango.customer.infrastructure.rest.campaign;

import lombok.Data;

@Data
public class CreateSloganDto {

  private Long userId;

  private String slogan;

}
