package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    public void init() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldGetVoteWhenGivenUserIdAndEventId() throws Exception {
        UserEntity user = UserEntity.builder()
                .username("user 0")
                .gender("male")
                .age(20)
                .email("12@34.com")
                .phone("13579246810")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        RsEventEntity event = RsEventEntity.builder()
                .eventName("event 0")
                .keyWord("key")
                .userId(String.valueOf(user.getId()))
                .build();
        event = rsEventRepository.save(event);
        VoteEntity vote0 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(0)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote0);

        VoteEntity vote1 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(1)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote1);

        VoteEntity vote2 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(2)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote2);

        VoteEntity vote3 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(3)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote3);

        VoteEntity vote4 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(4)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote4);

        VoteEntity vote5 = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .num(5)
                .user(user)
                .rsEvent(event)
                .build();
        voteRepository.save(vote5);

        mockMvc.perform(get("/vote?userId=" + user.getId() + "&rsEventId=" + event.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        mockMvc.perform(get("/vote?userId=" + user.getId() + "&rsEventId=" + event.getId() + "&pageIndex=" + 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldGetVoteBetweenTime() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        UserEntity user = UserEntity.builder()
                .username("user 0")
                .gender("male")
                .age(20)
                .email("12@34.com")
                .phone("13579246810")
                .voteNum(3)
                .build();
        user = userRepository.save(user);
        RsEventEntity event = RsEventEntity.builder()
                .eventName("event 0")
                .keyWord("key")
                .userId(String.valueOf(user.getId()))
                .build();
        event = rsEventRepository.save(event);
        VoteEntity vote = VoteEntity.builder()
                .num(2)
                .localDateTime(LocalDateTime.now())
                .rsEvent(event)
                .user(user)
                .build();
        voteRepository.save(vote);
        VoteEntity vote2 = VoteEntity.builder()
                .num(3)
                .localDateTime(LocalDateTime.now())
                .rsEvent(event)
                .user(user)
                .build();
        voteRepository.save(vote2);

        LocalDateTime end = LocalDateTime.now();
        mockMvc.perform(get("/rs/vote?start=" + start + "&end=" + end))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }
}
