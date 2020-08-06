package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;

@RestController
@Data
public class UserController {

    public static List<User> users =new ArrayList<>();

    @PostMapping("/user")
    public static void register(@RequestBody @Valid User user) {
        users.add(user);
    }

    @GetMapping("/user/getAll")
    public static ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(users);
    }


    private  final UserRepository userRepository;

    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /*
    @PostMapping("/user")
    public void register(@RequestBody @Valid User user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVotenum())
                .build();

        userRepository.save(entity);
    }

     */
}
