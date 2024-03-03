package com.mango.customer.infrastructure.rest.campaign;

import com.mango.customer.application.slogan.CreateSloganUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class CampaignController {

  @Autowired
  private CreateSloganUseCase createSloganUseCase;

  @PostMapping("/slogan")
  @ResponseStatus(HttpStatus.CREATED)
  public void createSlogan(@RequestBody CreateSloganDto createSloganDto) {
    createSloganUseCase.createSlogan(createSloganDto.getUserId(), createSloganDto.getSlogan());
  }

}
