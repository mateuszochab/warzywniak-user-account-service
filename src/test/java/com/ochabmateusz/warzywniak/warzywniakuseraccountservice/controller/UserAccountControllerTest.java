package com.ochabmateusz.warzywniak.warzywniakuseraccountservice.controller;

import com.google.gson.Gson;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.entity.User;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.UserRepository;
import com.ochabmateusz.warzywniak.warzywniakuseraccountservice.repository.ValidationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest
@WebMvcTest(UserAccountController.class)
class UserAccountControllerTest {

    private User user;
    String json1, json2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ValidationRepository validationRepository;


    @BeforeEach
    void setUp() {

        user = new User();
        Map<String, Object> map = new HashMap<>();
        map.put("a", "aa");

        Gson gson1 = new Gson();
        json1 = gson1.toJson(map);


        map.put("b", "bb");
        Gson gson2 = new Gson();
        json2 = gson2.toJson(map);


    }

    @AfterEach
    void cleanUp() {


        json1 = "";
        json2 = "";

    }


    @Test
    void confirmUser() throws Exception {

        User userConfirmed = user;

        userConfirmed.setConfirmed(true);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/confirmUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON);


        when(validationRepository.twoValidItemsInRequest("a", "b", new HashMap<>())).thenReturn(true);
        when(userRepository.returnUserFromDb("aadd")).thenReturn(user);
        user.setConfirmed(true);
        when(userRepository.confirmUserAndReturn(user)).thenReturn(userConfirmed);

        doNothing().when(userRepository).saveUserInDb(userConfirmed);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(status().reason("User has been successfully confirmed"))
                .andReturn();
    }

    @Test
    void changeName() throws Exception {


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/changeName")
                .accept(MediaType.APPLICATION_JSON)
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON);

        when(validationRepository.twoValidItemsInRequest("a", "b", new HashMap<>())).thenReturn(true);
        when(userRepository.returnUserFromDb("aadd")).thenReturn(user);
        when(userRepository.changeUsername(user, "aaaa")).thenReturn(user);

        doNothing().when(userRepository).saveUserInDb(user);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(status().reason("Username has been successfully changed"))
                .andReturn();
    }

    @Test
    void changePassword() {
    }


    //Todo more tests
}