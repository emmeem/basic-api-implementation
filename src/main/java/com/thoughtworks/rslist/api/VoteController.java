package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VoteController {
    private final VoteRepository voteRepository;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/vote")
    List<Vote> findByUserAndEvent(
            @RequestParam("userId") String userId,
            @RequestParam("rsEventId") String rsEventId,
            @RequestParam(value = "pageIndex", defaultValue = "1") String pageIndex) {
        Pageable pageable = PageRequest.of(Integer.parseInt(pageIndex) - 1, 5);
        List<VoteEntity> entities = voteRepository.findByUserIdAndRsEventId(
                Integer.valueOf(userId),
                Integer.valueOf(rsEventId),
                pageable);
        return entities.stream().map(vote -> Vote.builder()
                .userId(vote.getUser().getId())
                .rsEventId(vote.getRsEvent().getId())
                .time(vote.getLocalDateTime())
                .voteNum(vote.getNum())
                .build()).collect(Collectors.toList());
    }
}
