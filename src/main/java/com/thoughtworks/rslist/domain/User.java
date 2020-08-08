package com.thoughtworks.rslist.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
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
}
