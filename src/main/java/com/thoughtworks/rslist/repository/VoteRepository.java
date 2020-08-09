package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VoteRepository extends PagingAndSortingRepository<VoteEntity, Integer> {
    @Query("SELECT v FROM VoteEntity AS v WHERE v.num = :num")
    List<VoteEntity> findAllByAaa(int num);

    List<VoteEntity> findAll();

    List<VoteEntity> findByUserIdAndRsEventId(Integer userId, Integer rsEventId, Pageable pageable);
}
