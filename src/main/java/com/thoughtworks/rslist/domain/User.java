package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Size(max = 8)
    @NotEmpty
    @JsonProperty(value = "user_name")
    private String name;

    @NotEmpty
    @JsonProperty(value = "user_gender")
    private String gender;

    @Min(18)
    @Max(100)
    @JsonProperty(value = "user_age")
    private int age;

    @Email
    @JsonProperty(value = "user_email")
    private String email;

    @Pattern(regexp = "1\\d{10}")
    @NotEmpty
    @JsonProperty(value = "user_phone")
    private String phone;
}
