package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String username;

    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userId")
    private List<RsEventEntity> eventEntity;
}
