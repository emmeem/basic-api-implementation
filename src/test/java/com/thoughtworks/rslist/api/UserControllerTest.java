package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

    }

    @Test
    void shouldRegisterUser() throws Exception {
        User user = new User("name 0","male",19,"test@a.com","18888888888",10);
        String request = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals("name 0", users.get(0).getUsername());
    }
    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(get("/user/getAll"))
                .andExpect(jsonPath("$[0].username", is("liao")))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].age", is(25)))
                .andExpect(jsonPath("$[0].email", is("liao@a.com")))
                .andExpect(jsonPath("$[0].phone", is("17777777777")))
                .andExpect(jsonPath("$[0].voteNum", is(10)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .username("liao")
                .gender("male")
                .age(25)
                .email("liao@a.com")
                .phone("17777777777")
                .voteNum(10)
                .build();
        userRepository.save(userEntity);

        String userId = String.valueOf(userEntity.getId());
        mockMvc.perform(delete("/user/"+ userId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/getAll"))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    void shouldDeleteAllEventWhenDeleteUser() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .username("liao")
                .gender("male")
                .age(25)
                .email("liao@a.com")
                .phone("17777777777")
                .voteNum(10)
                .build();
        userRepository.save(userEntity);
        String userId = String.valueOf(userEntity.getId());

        RsEventEntity eventEntity = RsEventEntity.builder()
                .eventName("name 0")
                .keyWord("无分类")
                .userId(String.valueOf(userId))
                .build();
        rsEventRepository.save(eventEntity);

        mockMvc.perform(delete("/user/"+userId))
                .andExpect(status().isOk());

        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(0,events.size());
    }

    /*
    @Test
    void nameshouldNotLongThan8() throws Exception {
        User user = new User("eightliao", "male", 12, "eightliao@a.com", "18883333333",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid user")));
    }

    @Test
    void ageshouldBetween18and100() throws Exception {
        User user = new User("ageliao", "male", 109, "ageliao@a.com", "17883333333",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        assertEquals(0, UserController.users.size());
    }

    @Test
    void emailshouldCorrect() throws Exception {
        User user = new User("emailliao", "male", 19, "emailliao", "16883333333",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void phonenumbershouldCorrect() throws Exception {
        User user = new User("pnliao", "male", 19, "pnliao@a.com", "26883333333",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void gendershouldNotEmpty() throws Exception {
        User user = new User("genderliao", "", 19, "gdliao@a.com", "15883333333",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nameshouldNotEmpty() throws Exception {
        User user = new User("", "male", 19, "neliao@a.com", "14883333333",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void phonenumbershouldNotEmpty() throws Exception {
        User user = new User("anliao", "male", 19,"anliao@a.com", "",10);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User user = new User("liao", "male", 25, "liao@a.com", "12345678910",10);
        UserController.register(user);
        mockMvc.perform(get("/user/getAll"))

                .andExpect(jsonPath("$[0].name",is("liao")))
                .andExpect(jsonPath("$[0].gender",is("male")))
                .andExpect(jsonPath("$[0].age",is(25)))
                .andExpect(jsonPath("$[0].email",is("liao@a.com")))
                .andExpect(jsonPath("$[0].phone",is("12345678910")))
                .andExpect(jsonPath("$[0].voteNum",is(10)))
    }
    */

}
