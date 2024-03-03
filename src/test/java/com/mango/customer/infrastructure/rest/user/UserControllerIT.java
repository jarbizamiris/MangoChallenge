package com.mango.customer.infrastructure.rest.user;

import com.mango.customer.TestMangoApplication;
import com.mango.customer.application.exceptions.ApplicationNotFoundException;
import com.mango.customer.application.exceptions.ApplicationValidationException;
import com.mango.customer.infrastructure.exceptions.InfrastructureException;
import java.util.Optional;
import net.minidev.json.JSONObject;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mango.customer.domain.User;
import com.mango.customer.domain.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = TestMangoApplication.class)
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void signin() throws Exception {
        // GIVEN
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", "name");
        jsonObject.put("lastName", "lastName");

        String jsonString = jsonObject.toString();
        // WHEN
        ResultActions resultActions = mvc
            .perform(
                MockMvcRequestBuilders
                    .post("/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString)
            )
            .andDo(MockMvcResultHandlers.print());

        // THEN
        resultActions
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(jsonPath("$.name").value("name"))
            .andExpect(jsonPath("$.lastName").value("lastName"));

    }

    @Test
    public void getByUserId() throws Exception {
        // GIVEN
        String name = "name";
        String lastName = "lastName";

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);

        Long userId = userRepository.save(user).getId();

        // WHEN
        ResultActions resultActions = mvc
            .perform(
                MockMvcRequestBuilders
                    .get("/user/" + userId)
            )
            .andDo(MockMvcResultHandlers.print());

        // THEN
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.lastName").value(lastName))
        ;
    }

    @Test
    public void getByUserId_NonExistingUser() throws Exception {
        // GIVEN
        Long userId = 999L;

        // WHEN
        ResultActions resultActions = mvc
            .perform(
                MockMvcRequestBuilders
                    .get("/user/" + userId)
            )
            .andDo(MockMvcResultHandlers.print());

        // THEN
        resultActions
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ApplicationNotFoundException))
            .andExpect(result -> assertEquals("User: " + userId + " not found", result.getResolvedException().getMessage()));
        ;
    }

    @Test
    public void UpdateUser() throws Exception {
        // GIVEN
        JSONObject jsonObject = new JSONObject();

        String name = "name";
        String lastName = "lastName";
        String emailAddress = "test@update";

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);

        Long userId = userRepository.save(user).getId();

        jsonObject.put("id", String.valueOf(userId));
        jsonObject.put("emailAddress", emailAddress);

        String jsonString = jsonObject.toString();

        // WHEN
        ResultActions resultActions = mvc
            .perform(
                MockMvcRequestBuilders
                    .put("/updateUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString)
            )
            .andDo(MockMvcResultHandlers.print());

        // THEN
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<User> userById = userRepository.getUserById(userId);
        assertTrue(userById.isPresent());
        assertEquals(userById.get().getEmailAddress(), emailAddress);

    }

    @Test
    public void UpdateUser_NonExistingUser() throws Exception {
        // GIVEN
        JSONObject jsonObject = new JSONObject();

        // Este usuario no existe
        jsonObject.put("userId", "999");
        jsonObject.put("emailAddress", "test@update");

        String jsonString = jsonObject.toString();

        // WHEN
        ResultActions resultActions = mvc
            .perform(
                MockMvcRequestBuilders
                    .put("/updateUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString)
            )
            .andDo(MockMvcResultHandlers.print());

        // THEN
        resultActions
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InfrastructureException))
            .andExpect(result -> assertEquals("Customer to update not found", result.getResolvedException().getMessage()));
    }

}
