package com.thoughtworks.rslist.domain;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class User {

    public User() {

    }
    public User(String name,String gender,int age, String email,String phone, int voteNum) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.voteNum = voteNum;
    }
    @Size(max = 8)
    @NotEmpty
    private String name;

    @NotEmpty
    private String gender;

    @Min(18)
    @Max(100)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "1\\d{10}")
    @NotEmpty
    private String phone;

    private int voteNum;
}
