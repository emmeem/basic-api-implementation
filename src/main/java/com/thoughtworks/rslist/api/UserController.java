package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import com.thoughtworks.rslist.exception.CommenError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@Data
public class UserController {
    /*
    public static List<User> users =new ArrayList<>();

    @PostMapping("/user")
    public static void register(@RequestBody @Valid User user) {
        users.add(user);
    }

    @GetMapping("/user/getAll")
    public static ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(users);
    }
    */

    private  final UserRepository userRepository;
    private  final RsEventRepository rsEventRepository;

    public UserController(UserRepository userRepository,RsEventRepository rsEventRepository) {

        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @PostMapping("/user")
    public void register(@RequestBody @Valid User user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build();

        userRepository.save(entity);
    }

    @GetMapping("/user/getAll")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @DeleteMapping("/user/{index}")
    public void deleteUser(@PathVariable Integer index) {
        userRepository.deleteById(index);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException ex) {
        CommenError commentError =  new CommenError();
        String errorMessage = "invalid user";
        commentError.setError(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentError);

    }
}
