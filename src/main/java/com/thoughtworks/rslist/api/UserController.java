package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
<<<<<<< HEAD
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
=======
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
>>>>>>> error-handling
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;

@RestController
@Data
public class UserController {
    private  final UserRepository userRepository;

    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
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

    @DeleteMapping("/user/{index}")
    public void deleteUser(@PathVariable Integer index) {
        userRepository.deleteById(index);
    }

    @GetMapping("/user/getAll")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException ex) {
        CommenError commentError =  new CommenError();
        String errorMessage = "invalid user";
        commentError.setError(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentError);
    }
}
