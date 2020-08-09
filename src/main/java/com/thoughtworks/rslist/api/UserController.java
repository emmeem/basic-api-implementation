package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;

@RestController
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException ex) {
        CommenError commentError =  new CommenError();
        String errorMessage = "invalid user";
        commentError.setError(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentError);
    }
}
