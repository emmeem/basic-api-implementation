package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    @AfterEach
    void cleanup() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void get_event_list() throws Exception {
        mockMvc.perform(get("/rs/lists"));
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("第一条事件")))
                .andExpect(jsonPath("$.keyWord",is("无分类")))
                .andExpect(jsonPath("$",not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenIndexOutOfBound() throws Exception {
        mockMvc.perform(get("/rs/10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid index")));
    }

    @Test
    void shouldGetRangeRsEvent() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord", is("无分类")))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无分类")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBadRequestWhenStartAndEndOutOfBound() throws Exception {
        mockMvc.perform(get("/rs/list?start=-1&end=3"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",is("invalid request param")));
    }

    @Test
    void shouldAddRsEventWhenUserExists() throws Exception {
        UserEntity user = UserEntity.builder()
                .username("liao")
                .gender("male")
                .age(25)
                .email("liao@a.com")
                .phone("18888888888")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        String userId = String.valueOf(user.getId());
        String json = "{\"eventName\":\"event 0\",\"keyword\":\"key\",\"userId\":" + userId + "}";
        mockMvc.perform(post("/rs/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(1, events.size());
        assertEquals("event 0", events.get(0).getEventName());
    }

    @Test
    void shouldChangeRsEvent() throws Exception {
        UserEntity user = UserEntity.builder()
                .username("liao")
                .gender("male")
                .age(25)
                .email("liao@a.com")
                .phone("18888888888")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        String userId = String.valueOf(user.getId());
        RsEvent rsEvent = new RsEvent("修改第一个事件", "无分类", "user");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(put("/rs/1").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName",is("修改第一个事件")))
                .andExpect(jsonPath("$.keyWord",is("无分类")));
    }

    @Test
    void shouldUpdateRsEvent() throws Exception {
        UserEntity user = UserEntity.builder()
                .username("liao")
                .gender("male")
                .age(25)
                .email("liao@a.com")
                .phone("18888888888")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        String userId = String.valueOf(user.getId());
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .eventName("更新热搜事件")
                .keyWord("无分类")
                .userId(userId)
                .build();
        rsEventEntity = rsEventRepository.save(rsEventEntity);
        String rsEventId = String.valueOf(rsEventEntity.getId());

        String json = "{\"eventName\":\"更新热搜事件\",\"keyword\":\"无分类\",\"userId\":" + userId + "}";
        mockMvc.perform(patch("/rs/"+rsEventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        List<RsEventEntity> events = rsEventRepository.findAll();
        assertEquals(1, events.size());
        assertEquals("更新热搜事件", events.get(0).getEventName());
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        mockMvc.perform(delete("/rs/1"))
                .andExpect(status().isOk());
    }

    

}


