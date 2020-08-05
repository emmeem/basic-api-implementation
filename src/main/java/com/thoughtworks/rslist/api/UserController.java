package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;

@RestController
public class UserController {

    public static List<User> users =new ArrayList<>();

    @PostMapping("/user")
    public void register(@RequestBody User user) {
        users.add(user);
    }
}
