package com.thoughtworks.rslist;

import com.thoughtworks.rslist.api.RsController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/1"));
    }

    @Test
    void shouldGetRangeRsEvent() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(content().string("[第一条事件, 第二条事件]"))
               .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(content().string("[第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddOneRsEvent() throws Exception {
        mockMvc.perform(post("/rs/event").content("第四条事件"))
               .andExpect(status().isOk());
    }

    @Test
    void shouldChangeRsEvent() throws Exception {
        mockMvc.perform(put("/rs/1").content("修改事件"))
                .andExpect(status().isOk());
    }

    @Test
    public void get_Hot_search_event_list() throws Exception {
        mockMvc.perform(get("/rs/lists"))
                .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"))
                .andExpect(status().isOk())
                .andReturn();
    }
}

