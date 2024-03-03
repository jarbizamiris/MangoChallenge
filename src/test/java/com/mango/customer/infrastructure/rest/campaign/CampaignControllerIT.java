package com.mango.customer.infrastructure.rest.campaign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mango.customer.TestMangoApplication;
import com.mango.customer.application.exceptions.ApplicationValidationException;
import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;
import com.mango.customer.domain.exceptions.DomainValidationException;
import java.util.Optional;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = TestMangoApplication.class)
@AutoConfigureMockMvc
public class CampaignControllerIT {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void createSlogan() throws Exception {
    // GIVEN
    String name = "name";
    String lastName = "lastName";

    User user = new User();
    user.setName(name);
    user.setLastName(lastName);

    Long id = userRepository.save(user).getId();
    String userId = String.valueOf(id);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("userId", userId);
    jsonObject.put("slogan", "slogan test");

    String jsonString = jsonObject.toString();

    // WHEN
    ResultActions resultActions = mvc
        .perform(
            MockMvcRequestBuilders
                .post("/slogan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        )
        .andDo(MockMvcResultHandlers.print());

    // THEN
    resultActions
        .andExpect(MockMvcResultMatchers.status().isCreated());

    Optional<User> userById = userRepository.getUserById(id);
    assertTrue(userById.isPresent());
    assertTrue(userById.get().getSlogans().contains("slogan test"));

  }

  @Test
  public void createSlogan_NonExistUser() throws Exception {
    // GIVEN
    JSONObject jsonObject = new JSONObject();

    // Este usuario no existe
    jsonObject.put("userId", "999");
    jsonObject.put("slogan", "slogan test");

    String jsonString = jsonObject.toString();

    // WHEN
    ResultActions resultActions = mvc
        .perform(
            MockMvcRequestBuilders
                .post("/slogan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        )
        .andDo(MockMvcResultHandlers.print());

    // THEN
    resultActions
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApplicationValidationException))
        .andExpect(result -> assertEquals("User: 999 not found", result.getResolvedException().getMessage()));
  }

  @Test
  public void createSlogan_MAXSLOGAN_true() throws Exception {
    // GIVEN
    int MAX_SLOGANS = 3;

    String name = "name";
    String lastName = "lastName";

    User user = new User();
    user.setName(name);
    user.setLastName(lastName);

    for (int i = 0; i < User.MAX_SLOGANS; i++) {
      user.addSlogan("Slogan " + (i + 1));
    }

    Long id = userRepository.save(user).getId();

    String userId = String.valueOf(id);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("userId", userId);
    jsonObject.put("slogan", "slogan 4");

    String jsonString = jsonObject.toString();

    // WHEN
    ResultActions resultActions = mvc
        .perform(
            MockMvcRequestBuilders
                .post("/slogan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        )
        .andDo(MockMvcResultHandlers.print());

    // THEN
    resultActions
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DomainValidationException))
        .andExpect(result -> assertEquals("You have reached the max slogan possible (" + MAX_SLOGANS + ")", result.getResolvedException().getMessage()));
  }

}
