package com.thoughtworks.rslist.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class User {

    public User() {

    }
    public User(String name,String gender,int age, String email,String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }
    @Size(max = 8)
    private String name;

    private String gender;

    @Min(18)
    @Max(100)
    private int age;

    @Email
    private String email;

    private String phone;
}
